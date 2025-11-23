package com.flextime.service;

import org.springframework.stereotype.Service;

@Service
public class IaRecomendacaoService {

    public String recomendarUso(Long userId) {
        // IA desativada: devolve só uma mensagem padrão.
        return "A funcionalidade de recomendação por IA está desativada nesta versão da aplicação.";
    }
}
