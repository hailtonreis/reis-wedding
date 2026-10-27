package br.com.reis_wedding.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.reis_wedding.dto.AcompanhanteRequestDTO;
import br.com.reis_wedding.dto.AcompanhanteResponseDTO;
import br.com.reis_wedding.entity.Acompanhante;
import br.com.reis_wedding.service.AcompanhanteService;

@RestController
@RequestMapping("/acompanhantes")
public class AcompanhanteController {

    private final AcompanhanteService acompanhanteService;

    public AcompanhanteController(AcompanhanteService acompanhanteService) {
        this.acompanhanteService = acompanhanteService;
    }

    @PostMapping("/convite/{conviteId}")
    public ResponseEntity<AcompanhanteResponseDTO> cadastrar(
            @PathVariable Long conviteId,
            @RequestBody AcompanhanteRequestDTO request) {

        Acompanhante acompanhante = new Acompanhante();
        acompanhante.setNome(request.getNome());

        Acompanhante acompanhanteSalvo =
                acompanhanteService.salvar(conviteId, acompanhante);

        AcompanhanteResponseDTO response = new AcompanhanteResponseDTO();

        response.setId(acompanhanteSalvo.getId());
        response.setConviteId(acompanhanteSalvo.getConvite().getId());
        response.setNome(acompanhanteSalvo.getNome());
        response.setCreatedAt(acompanhanteSalvo.getCreatedAt());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/convite/{conviteId}")
    public ResponseEntity<List<AcompanhanteResponseDTO>> listarPorConvite(
            @PathVariable Long conviteId) {

        return ResponseEntity.ok(
                acompanhanteService.listarPorConvite(conviteId)
        );
    }
}