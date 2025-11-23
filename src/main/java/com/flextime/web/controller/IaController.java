package com.flextime.web.controller;

import com.flextime.service.IaRecomendacaoService;
import com.flextime.domain.dto.AiSugestaoRequest;
import com.flextime.domain.dto.AiSugestaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class IaController {

    private final IaRecomendacaoService iaRecomendacaoService;

    public IaController(IaRecomendacaoService iaRecomendacaoService) {
        this.iaRecomendacaoService = iaRecomendacaoService;
    }

    @PostMapping("/sugestoes-produtividade")
    public ResponseEntity<AiSugestaoResponse> gerarSugestao(@RequestBody @Valid AiSugestaoRequest request) {

        String textoIa = iaRecomendacaoService
                .gerarSugestaoProdutividade(request.getNomeUsuario());

        return ResponseEntity.ok(new AiSugestaoResponse(textoIa));
    }
}
