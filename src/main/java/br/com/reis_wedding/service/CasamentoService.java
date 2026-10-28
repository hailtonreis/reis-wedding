package br.com.reis_wedding.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.reis_wedding.entity.Casamento;
import br.com.reis_wedding.exception.RecursoNaoEncontradoException;
import br.com.reis_wedding.repository.CasamentoRepository;

@Service
public class CasamentoService {

    private final CasamentoRepository casamentoRepository;

    public CasamentoService(CasamentoRepository casamentoRepository) {
        this.casamentoRepository = casamentoRepository;
    }

    @Transactional
    public Casamento salvar(Casamento casamento) {

        validarDatas(casamento);

        return casamentoRepository.save(casamento);
    }

    public List<Casamento> listarTodos() {
        return casamentoRepository.findAll();
    }

    public Casamento buscarPorId(Long id) {

        return casamentoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Casamento não encontrado"));
    }

    @Transactional
    public Casamento atualizar(Long id, Casamento dados) {

        Casamento casamento = buscarPorId(id);

        casamento.setNomeNoivo(dados.getNomeNoivo());
        casamento.setNomeNoiva(dados.getNomeNoiva());
        casamento.setDataEvento(dados.getDataEvento());
        casamento.setDataLimiteRsvp(dados.getDataLimiteRsvp());

        validarDatas(casamento);

        return casamentoRepository.save(casamento);
    }

    private void validarDatas(Casamento casamento) {

        if (casamento.getDataEvento() == null) {
            throw new IllegalArgumentException(
                    "A data do casamento é obrigatória.");
        }

        if (casamento.getDataLimiteRsvp() == null) {
            throw new IllegalArgumentException(
                    "A data-limite do RSVP é obrigatória.");
        }

        if (!casamento.getDataLimiteRsvp()
                .isBefore(casamento.getDataEvento())) {

            throw new IllegalArgumentException(
                    "A data-limite do RSVP deve ser anterior à data do casamento.");
        }
    }
}