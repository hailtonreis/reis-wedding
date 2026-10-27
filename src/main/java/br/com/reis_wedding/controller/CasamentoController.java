package br.com.reis_wedding.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.reis_wedding.entity.Casamento;
import br.com.reis_wedding.service.CasamentoService;

@RestController
@RequestMapping("/casamentos")
public class CasamentoController {
	  private final CasamentoService casamentoService;

	    public CasamentoController(CasamentoService casamentoService) {
	        this.casamentoService = casamentoService;
	    }

	    @PostMapping
	    public ResponseEntity<Casamento> cadastrar(
	            @RequestBody Casamento casamento) {

	        Casamento casamentoSalvo = casamentoService.salvar(casamento);

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(casamentoSalvo);
	    }

	    @GetMapping
	    public ResponseEntity<List<Casamento>> listarTodos() {

	        return ResponseEntity.ok(casamentoService.listarTodos());
	    }
}
