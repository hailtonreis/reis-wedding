package br.com.reis_wedding.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================================================
    // REGRAS DE NEGÓCIO - HTTP 400
    // =========================================================

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, String>> tratarRegraNegocio(
            RegraNegocioException exception) {

        Map<String, String> erro = Map.of(
                "mensagem", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    // =========================================================
    // RECURSO NÃO ENCONTRADO - HTTP 404
    // =========================================================

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException exception) {

        Map<String, String> erro = Map.of(
                "mensagem", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    // =========================================================
    // ARGUMENTOS INVÁLIDOS - HTTP 400
    // =========================================================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarArgumentoInvalido(
            IllegalArgumentException exception) {

        Map<String, String> erro = Map.of(
                "mensagem", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    // =========================================================
    // CONFLITOS DE ESTADO - HTTP 409
    // =========================================================

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> tratarEstadoInvalido(
            IllegalStateException exception) {

        Map<String, String> erro = Map.of(
                "mensagem", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    // FIM DO ARQUIVO
}