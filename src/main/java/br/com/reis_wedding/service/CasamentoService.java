package br.com.reis_wedding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.reis_wedding.entity.Casamento;
import br.com.reis_wedding.repository.CasamentoRepository;

@Service
public class CasamentoService {
	
	private final CasamentoRepository casamentoRepository;

    public CasamentoService(CasamentoRepository casamentoRepository) {
        this.casamentoRepository = casamentoRepository;
    }

    public Casamento salvar(Casamento casamento) {
        return casamentoRepository.save(casamento);
    }

    public List<Casamento> listarTodos() {
        return casamentoRepository.findAll();
    }

}
