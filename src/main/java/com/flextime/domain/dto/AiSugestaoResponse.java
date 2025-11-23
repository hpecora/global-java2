package com.flextime.domain.dto;

public class AiSugestaoResponse {

    private String sugestao;

    public AiSugestaoResponse() {
    }

    public AiSugestaoResponse(String sugestao) {
        this.sugestao = sugestao;
    }

    public String getSugestao() {
        return sugestao;
    }

    public void setSugestao(String sugestao) {
        this.sugestao = sugestao;
    }
}
