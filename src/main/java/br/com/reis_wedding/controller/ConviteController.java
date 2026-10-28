package br.com.reis_wedding.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.reis_wedding.dto.ConviteResponseDTO;
import br.com.reis_wedding.dto.ConfirmacaoPresencaRequestDTO;

import br.com.reis_wedding.entity.Convite;
import br.com.reis_wedding.service.ConviteService;

// =========================================================
// CONTROLLER - CONVITES
// Reis Wedding
// =========================================================

@RestController
@RequestMapping("/convites")
@CrossOrigin(
    origins = "http://localhost:4200",
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.OPTIONS
    }
)
public class ConviteController {

    private final ConviteService conviteService;

    // =========================================================
    // CONSTRUTOR
    // =========================================================

    public ConviteController(ConviteService conviteService) {
        this.conviteService = conviteService;
    }

    // =========================================================
    // CADASTRAR CONVITE
    // =========================================================

    @PostMapping("/casamento/{casamentoId}")
    public ResponseEntity<ConviteResponseDTO> cadastrar(
            @PathVariable Long casamentoId,
            @RequestBody Convite convite) {

        ConviteResponseDTO conviteSalvo = conviteService.salvar(
                casamentoId,
                convite
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conviteSalvo);
    }

    // =========================================================
    // LISTAR CONVITES POR CASAMENTO
    // =========================================================

    @GetMapping("/casamento/{casamentoId}")
    public ResponseEntity<List<ConviteResponseDTO>> listarPorCasamento(
            @PathVariable Long casamentoId) {

        return ResponseEntity.ok(
                conviteService.listarPorCasamento(casamentoId)
        );
    }

    // =========================================================
    // BUSCAR CONVITE PELO TOKEN
    // =========================================================

    @GetMapping("/token/{token}")
    public ResponseEntity<ConviteResponseDTO> buscarPorToken(
            @PathVariable String token) {

        return ResponseEntity.ok(
                conviteService.buscarPorToken(token)
        );
    }

    // =========================================================
    // CONFIRMAR PRESENÇA PELO TOKEN
    // Permite cadastrar e atualizar acompanhantes
    // =========================================================

    @PutMapping("/token/{token}/confirmar")
    public ResponseEntity<ConviteResponseDTO> confirmarPresencaPorToken(
            @PathVariable String token,
            @RequestBody ConfirmacaoPresencaRequestDTO request) {

        return ResponseEntity.ok(
                conviteService.confirmarPresencaPorToken(
                        token,
                        request
                )
        );
    }

    // =========================================================
    // RECUSAR PRESENÇA PELO TOKEN
    // =========================================================

    @PutMapping("/token/{token}/recusar")
    public ResponseEntity<ConviteResponseDTO> recusarPresencaPorToken(
            @PathVariable String token) {

        return ResponseEntity.ok(
                conviteService.recusarPresencaPorToken(token)
        );
    }

    // =========================================================
    // CONFIRMAR PRESENÇA PELO ID
    // =========================================================

    @PutMapping("/{conviteId}/confirmar")
    public ResponseEntity<ConviteResponseDTO> confirmarPresenca(
            @PathVariable Long conviteId) {

        return ResponseEntity.ok(
                conviteService.confirmarPresenca(conviteId)
        );
    }

    // =========================================================
    // RECUSAR PRESENÇA PELO ID
    // =========================================================

    @PutMapping("/{conviteId}/recusar")
    public ResponseEntity<ConviteResponseDTO> recusarPresenca(
            @PathVariable Long conviteId) {

        return ResponseEntity.ok(
                conviteService.recusarPresenca(conviteId)
        );
    }

    // =========================================================
    // ALTERAR LIMITE DE ACOMPANHANTES
    // =========================================================

    @PutMapping("/{conviteId}/limite-acompanhantes")
    public ResponseEntity<ConviteResponseDTO> alterarLimiteAcompanhantes(
            @PathVariable Long conviteId,
            @RequestBody Integer novoLimite) {

        return ResponseEntity.ok(
                conviteService.alterarLimiteAcompanhantes(
                        conviteId,
                        novoLimite
                )
        );
    }

    // =========================================================
    // FIM DO ARQUIVO
    // =========================================================
}