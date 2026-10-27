package br.com.reis_wedding.dto;

import java.util.List;

public class ConfirmacaoPresencaRequestDTO {

    private List<String> acompanhantes;

    public List<String> getAcompanhantes() {
        return acompanhantes;
    }

    public void setAcompanhantes(List<String> acompanhantes) {
        this.acompanhantes = acompanhantes;
    }
}