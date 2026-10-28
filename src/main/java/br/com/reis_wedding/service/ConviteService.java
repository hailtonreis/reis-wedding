package br.com.reis_wedding.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.reis_wedding.dto.ConfirmacaoPresencaRequestDTO;
import br.com.reis_wedding.dto.ConviteResponseDTO;
import br.com.reis_wedding.entity.Acompanhante;
import br.com.reis_wedding.entity.Casamento;
import br.com.reis_wedding.entity.Convite;
import br.com.reis_wedding.enums.StatusConvite;
import br.com.reis_wedding.exception.RecursoNaoEncontradoException;
import br.com.reis_wedding.repository.AcompanhanteRepository;
import br.com.reis_wedding.repository.CasamentoRepository;
import br.com.reis_wedding.repository.ConviteRepository;

@Service
public class ConviteService {

    private static final ZoneId FUSO_HORARIO =
            ZoneId.of("America/Sao_Paulo");

    private final ConviteRepository conviteRepository;
    private final CasamentoRepository casamentoRepository;
    private final AcompanhanteRepository acompanhanteRepository;

    public ConviteService(
            ConviteRepository conviteRepository,
            CasamentoRepository casamentoRepository,
            AcompanhanteRepository acompanhanteRepository) {

        this.conviteRepository = conviteRepository;
        this.casamentoRepository = casamentoRepository;
        this.acompanhanteRepository = acompanhanteRepository;
    }

    // =========================================================
    // CADASTRAR CONVITE
    // =========================================================

    @Transactional
    public ConviteResponseDTO salvar(
            Long casamentoId,
            Convite convite) {

        Casamento casamento = casamentoRepository.findById(casamentoId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Casamento não encontrado"));

        convite.setCasamento(casamento);
        convite.setStatus(StatusConvite.PENDENTE);
        convite.setToken(UUID.randomUUID().toString());

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }

    // =========================================================
    // LISTAR TODOS OS CONVITES
    // =========================================================

    public List<ConviteResponseDTO> listarTodos() {

        return conviteRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // =========================================================
    // LISTAR CONVITES POR CASAMENTO
    // =========================================================

    public List<ConviteResponseDTO> listarPorCasamento(
            Long casamentoId) {

        return conviteRepository.findByCasamentoId(casamentoId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // =========================================================
    // CONFIRMAR PRESENÇA POR ID
    // =========================================================

    @Transactional
    public ConviteResponseDTO confirmarPresenca(Long conviteId) {

        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Convite não encontrado"));

        validarPrazoRsvp(convite);

        LocalDateTime agora = LocalDateTime.now(FUSO_HORARIO);

        if (convite.getStatus() != StatusConvite.CONFIRMADO) {

            convite.setStatus(StatusConvite.CONFIRMADO);
            convite.setDataConfirmacao(agora);
        }

        convite.setUpdatedAt(agora);

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }

    // =========================================================
    // RECUSAR PRESENÇA POR ID
    // =========================================================

    @Transactional
    public ConviteResponseDTO recusarPresenca(Long conviteId) {

        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Convite não encontrado"));

        validarPrazoRsvp(convite);

        LocalDateTime agora = LocalDateTime.now(FUSO_HORARIO);

        convite.setStatus(StatusConvite.RECUSADO);
        convite.setDataConfirmacao(null);
        convite.setUpdatedAt(agora);

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }

    // =========================================================
    // CONFIRMAR PRESENÇA POR TOKEN
    // COM GESTÃO DE ACOMPANHANTES
    // =========================================================

    @Transactional
    public ConviteResponseDTO confirmarPresencaPorToken(
            String token,
            ConfirmacaoPresencaRequestDTO request) {

        // Busca o convite pelo token
        Convite convite = conviteRepository.findByToken(token)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Convite não encontrado"));

        // Verifica a data-limite do RSVP
        validarPrazoRsvp(convite);

        // Valida a requisição
        if (request == null || request.getAcompanhantes() == null) {

            throw new IllegalArgumentException(
                    "A lista de acompanhantes deve ser informada.");
        }

        // Normaliza os nomes
        List<String> nomes = request.getAcompanhantes()
                .stream()
                .map(nome -> nome == null ? null : nome.trim())
                .toList();

        // Valida nomes nulos ou vazios
        if (nomes.stream().anyMatch(Objects::isNull)
                || nomes.stream().anyMatch(String::isBlank)) {

            throw new IllegalArgumentException(
                    "Todos os acompanhantes devem possuir um nome válido.");
        }

        // Valida o tamanho dos nomes
        if (nomes.stream().anyMatch(nome -> nome.length() > 150)) {

            throw new IllegalArgumentException(
                    "O nome do acompanhante não pode ultrapassar 150 caracteres.");
        }

        // Valida o limite de acompanhantes
        Integer limite = convite.getLimiteAcompanhantes();

        if (limite == null || limite < 0 || nomes.size() > limite) {

            throw new IllegalArgumentException(
                    "Quantidade de acompanhantes acima do limite permitido.");
        }

        // Remove acompanhantes cadastrados anteriormente
        acompanhanteRepository.deleteByConviteId(convite.getId());

        acompanhanteRepository.flush();

        // Prepara a nova lista
        List<Acompanhante> acompanhantes = new ArrayList<>();

        for (String nome : nomes) {

            Acompanhante acompanhante = new Acompanhante();

            acompanhante.setConvite(convite);
            acompanhante.setNome(nome);

            acompanhantes.add(acompanhante);
        }

        // Salva a lista atualizada
        acompanhanteRepository.saveAll(acompanhantes);

        LocalDateTime agora = LocalDateTime.now(FUSO_HORARIO);

        // Confirma a presença
        if (convite.getStatus() != StatusConvite.CONFIRMADO) {

            convite.setStatus(StatusConvite.CONFIRMADO);
            convite.setDataConfirmacao(agora);
        }

        convite.setUpdatedAt(agora);

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }

    // =========================================================
    // RECUSAR PRESENÇA POR TOKEN
    // =========================================================

    @Transactional
    public ConviteResponseDTO recusarPresencaPorToken(String token) {

        Convite convite = conviteRepository.findByToken(token)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Convite não encontrado"));

        // Verifica a data-limite do RSVP
        validarPrazoRsvp(convite);

        LocalDateTime agora = LocalDateTime.now(FUSO_HORARIO);

        convite.setStatus(StatusConvite.RECUSADO);
        convite.setDataConfirmacao(null);
        convite.setUpdatedAt(agora);

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }

    // =========================================================
    // BUSCAR CONVITE POR TOKEN
    // =========================================================

    public ConviteResponseDTO buscarPorToken(String token) {

        Convite convite = conviteRepository.findByToken(token)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Convite não encontrado"));

        return converterParaDTO(convite);
    }

    // =========================================================
    // ALTERAR LIMITE DE ACOMPANHANTES
    // =========================================================

    @Transactional
    public ConviteResponseDTO alterarLimiteAcompanhantes(
            Long conviteId,
            Integer novoLimite) {

        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Convite não encontrado"));

        if (novoLimite == null || novoLimite < 0) {

            throw new IllegalArgumentException(
                    "O limite de acompanhantes não pode ser negativo.");
        }

        convite.setLimiteAcompanhantes(novoLimite);
        convite.setUpdatedAt(LocalDateTime.now(FUSO_HORARIO));

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }

    // =========================================================
    // VALIDAR DATA-LIMITE DO RSVP
    // =========================================================

    private void validarPrazoRsvp(Convite convite) {

        LocalDate dataLimite =
                convite.getCasamento().getDataLimiteRsvp();

        if (dataLimite == null) {

            throw new IllegalStateException(
                    "A data-limite do RSVP não foi configurada para este casamento.");
        }

        LocalDate hoje = LocalDate.now(FUSO_HORARIO);

        if (hoje.isAfter(dataLimite)) {

            throw new IllegalStateException(
                    "O prazo para confirmação de presença e alterações foi encerrado.");
        }
    }

    // =========================================================
    // CONVERTER ENTIDADE PARA DTO
    // =========================================================

    private ConviteResponseDTO converterParaDTO(Convite convite) {

        ConviteResponseDTO dto = new ConviteResponseDTO();

        // Dados do convite
        dto.setId(convite.getId());
        dto.setCasamentoId(convite.getCasamento().getId());
        dto.setNomeConvidado(convite.getNomeConvidado());
        dto.setTelefone(convite.getTelefone());
        dto.setToken(convite.getToken());
        dto.setStatus(convite.getStatus());
        dto.setLimiteAcompanhantes(convite.getLimiteAcompanhantes());
        dto.setDataConfirmacao(convite.getDataConfirmacao());
        dto.setCreatedAt(convite.getCreatedAt());
        dto.setUpdatedAt(convite.getUpdatedAt());

        // Recupera os acompanhantes cadastrados
        List<String> nomesAcompanhantes = acompanhanteRepository
                .findByConviteId(convite.getId())
                .stream()
                .map(Acompanhante::getNome)
                .toList();

        dto.setAcompanhantes(nomesAcompanhantes);

        // Recupera a data-limite do casamento
        LocalDate dataLimite =
                convite.getCasamento().getDataLimiteRsvp();

        dto.setDataLimiteRsvp(dataLimite);

        // Verifica se o prazo está encerrado
        LocalDate hoje = LocalDate.now(FUSO_HORARIO);

        boolean prazoEncerrado =
                dataLimite == null || hoje.isAfter(dataLimite);

        dto.setPrazoRsvpEncerrado(prazoEncerrado);

        return dto;
    }

    // FIM DO ARQUIVO
}