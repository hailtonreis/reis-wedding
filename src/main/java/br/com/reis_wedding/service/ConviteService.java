package br.com.reis_wedding.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.reis_wedding.dto.ConfirmacaoPresencaRequestDTO;
import br.com.reis_wedding.dto.ConviteResponseDTO;
import br.com.reis_wedding.entity.Casamento;
import br.com.reis_wedding.entity.Convite;
import br.com.reis_wedding.enums.StatusConvite;
import br.com.reis_wedding.exception.RecursoNaoEncontradoException;
import br.com.reis_wedding.repository.AcompanhanteRepository;
import br.com.reis_wedding.repository.CasamentoRepository;
import br.com.reis_wedding.repository.ConviteRepository;


@Service
public class ConviteService {

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

    public ConviteResponseDTO salvar(Long casamentoId, Convite convite) {

        Casamento casamento = casamentoRepository.findById(casamentoId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Casamento não encontrado"));

        convite.setCasamento(casamento);
        convite.setStatus(StatusConvite.PENDENTE);
        convite.setToken(UUID.randomUUID().toString());

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }

    public List<ConviteResponseDTO> listarTodos() {
    	return conviteRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }
    
    public List<ConviteResponseDTO> listarPorCasamento(Long casamentoId) {

        return conviteRepository.findByCasamentoId(casamentoId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }
    
    public ConviteResponseDTO confirmarPresenca(Long conviteId) {

        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Convite não encontrado"));

        convite.setStatus(StatusConvite.CONFIRMADO);
        convite.setDataConfirmacao(LocalDateTime.now());
        convite.setUpdatedAt(LocalDateTime.now());

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }
    
    public ConviteResponseDTO recusarPresenca(Long conviteId) {

        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Convite não encontrado"));

        convite.setStatus(StatusConvite.RECUSADO);
        convite.setDataConfirmacao(null);
        convite.setUpdatedAt(LocalDateTime.now());
        
        Convite conviteSalvo = conviteRepository.save(convite);

        return  converterParaDTO(conviteSalvo);
    }
    
    public ConviteResponseDTO confirmarPresencaPorToken(
            String token,
            ConfirmacaoPresencaRequestDTO request) {

        Convite convite = conviteRepository.findByToken(token)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Convite não encontrado"));

        if (convite.getStatus() != StatusConvite.CONFIRMADO) {
            convite.setStatus(StatusConvite.CONFIRMADO);
            convite.setDataConfirmacao(LocalDateTime.now());
            convite.setUpdatedAt(LocalDateTime.now());
            convite = conviteRepository.save(convite);
        }

        return converterParaDTO(convite);
    }
    
    public ConviteResponseDTO recusarPresencaPorToken(String token) {
        Convite convite = conviteRepository.findByToken(token)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Convite não encontrado"));

        convite.setStatus(StatusConvite.RECUSADO);
        convite.setDataConfirmacao(null);
        convite.setUpdatedAt(LocalDateTime.now());

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }
    
    public ConviteResponseDTO buscarPorToken(String token) {
        Convite convite = conviteRepository.findByToken(token)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Convite não encontrado"));

        return converterParaDTO(convite);
    }
    
    private ConviteResponseDTO converterParaDTO(Convite convite) {

        ConviteResponseDTO dto = new ConviteResponseDTO();

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

        return dto;
    }
    
    public ConviteResponseDTO alterarLimiteAcompanhantes(
            Long conviteId,
            Integer novoLimite) {

        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Convite não encontrado"));

        convite.setLimiteAcompanhantes(novoLimite);
        convite.setUpdatedAt(LocalDateTime.now());

        Convite conviteSalvo = conviteRepository.save(convite);

        return converterParaDTO(conviteSalvo);
    }
    
}