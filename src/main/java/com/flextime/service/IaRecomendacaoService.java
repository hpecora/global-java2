package com.flextime.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IaRecomendacaoService {

    private final ChatClient chatClient;

    public IaRecomendacaoService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // 1) Endpoint “simples” de sugestão por nome (se quiser manter)
    public String gerarSugestaoProdutividade(String nomeUsuario) {

        String prompt = """
                Você é um assistente de produtividade.
                Dê recomendações curtas e práticas para melhorar a rotina de trabalho de %s,
                considerando foco, pausas, organização das tarefas e bem-estar emocional.
                Responda em português, em no máximo 5 frases.
                """.formatted(nomeUsuario != null ? nomeUsuario : "o usuário");

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    // 2) Método que a análise de produtividade usa, com os MESMOS parâmetros do service
    public String gerarRecomendacao(Long userId,
                                    LocalDate periodoInicio,
                                    LocalDate periodoFim,
                                    double mediaHumor,
                                    int scoreEquilibrio) {

        String prompt = """
                Você é um assistente de produtividade.
                Gere um resumo curto (no máximo 5 frases) sobre a rotina do usuário,
                com recomendações práticas para melhorar o equilíbrio entre trabalho e bem-estar.

                Dados do usuário:
                - ID: %d
                - Período analisado: %s até %s
                - Média de humor (0 a 10): %.2f
                - Score de equilíbrio (0 a 100): %d
                """.formatted(
                userId,
                periodoInicio,
                periodoFim,
                mediaHumor,
                scoreEquilibrio
        );

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
