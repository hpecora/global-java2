package com.flextime.service;

import com.flextime.domain.dto.AnaliseProdutividadeResponse;
import com.flextime.domain.dto.GerarAnaliseRequest;
import com.flextime.domain.model.AnaliseProdutividade;
import com.flextime.domain.model.Checkin;
import com.flextime.domain.repository.AnaliseProdutividadeRepository;
import com.flextime.domain.repository.CheckinRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnaliseProdutividadeService {

    private final CheckinRepository checkinRepository;
    private final AnaliseProdutividadeRepository analiseRepository;
    private final IaRecomendacaoService iaRecomendacaoService;

    public AnaliseProdutividadeService(
            CheckinRepository checkinRepository,
            AnaliseProdutividadeRepository analiseRepository,
            IaRecomendacaoService iaRecomendacaoService
    ) {
        this.checkinRepository = checkinRepository;
        this.analiseRepository = analiseRepository;
        this.iaRecomendacaoService = iaRecomendacaoService;
    }

    @Transactional
    public AnaliseProdutividadeResponse gerarAnalise(GerarAnaliseRequest request) {

        List<Checkin> checkins = checkinRepository.findByUserIdAndDateBetween(
                request.getUserId(),
                request.getPeriodoInicio(),
                request.getPeriodoFim()
        );

        if (checkins.isEmpty()) {
            throw new IllegalArgumentException("Nenhum check-in encontrado para o período informado.");
        }

        int total = checkins.size();
        int somaHumor = checkins.stream().mapToInt(Checkin::getMood).sum();
        double mediaHumor = (double) somaHumor / total;

        int scoreEquilibrio = calcularScore(mediaHumor);

        String resumo = iaRecomendacaoService.gerarRecomendacao(
                request.getUserId(),
                request.getPeriodoInicio(),
                request.getPeriodoFim(),
                mediaHumor,
                scoreEquilibrio
        );

        AnaliseProdutividade analise = new AnaliseProdutividade();
        analise.setUserId(request.getUserId());
        analise.setPeriodoInicio(request.getPeriodoInicio());
        analise.setPeriodoFim(request.getPeriodoFim());
        analise.setScoreEquilibrio(scoreEquilibrio);
        analise.setResumoTexto(resumo);

        analise = analiseRepository.save(analise);

        return toResponse(analise);
    }

    public Page<AnaliseProdutividadeResponse> listarPorUsuario(Long userId, Pageable pageable) {

        Page<AnaliseProdutividade> page = analiseRepository.findByUserId(userId, pageable);

        var dtoList = page.getContent()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    public AnaliseProdutividadeResponse buscarUltima(Long userId) {

        AnaliseProdutividade analise = analiseRepository
                .findFirstByUserIdOrderByCriadoEmDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma análise encontrada para este usuário."));

        return toResponse(analise);
    }

    private int calcularScore(double mediaHumor) {

        if (mediaHumor >= 8) return 90;
        if (mediaHumor >= 6) return 75;
        if (mediaHumor >= 4) return 55;
        return 35;
    }

    private AnaliseProdutividadeResponse toResponse(AnaliseProdutividade analise) {

        AnaliseProdutividadeResponse resp = new AnaliseProdutividadeResponse();
        resp.setId(analise.getId());
        resp.setUserId(analise.getUserId());
        resp.setPeriodoInicio(analise.getPeriodoInicio());
        resp.setPeriodoFim(analise.getPeriodoFim());
        resp.setScoreEquilibrio(analise.getScoreEquilibrio());
        resp.setResumoTexto(analise.getResumoTexto());
        resp.setCriadoEm(analise.getCriadoEm());

        return resp;
    }
}
