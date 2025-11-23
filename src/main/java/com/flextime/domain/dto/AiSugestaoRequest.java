package com.flextime.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class AiSugestaoRequest {

    @NotBlank(message = "{ia.nome.obrigatorio}")
    private String nomeUsuario;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
