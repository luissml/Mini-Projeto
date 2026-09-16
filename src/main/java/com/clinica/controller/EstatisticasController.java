package com.clinica.controller;

import com.clinica.service.ClinicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final ClinicaService clinicaService;
    private final String[] diasDaSemana = {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};

    public EstatisticasController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @GetMapping("/total")
    public ResponseEntity<Map<String, Object>> informarTotal() {
        int total = clinicaService.informarTotalPacientesAtendidos();
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("totalPacientesAtendidos", total);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/dia-maior")
    public ResponseEntity<Map<String, Object>> informarDiaMaior() {
        int indexDia = clinicaService.informarDiaComMaiorQuantidadeAtendimentos();
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("diaComMaiorAtendimento", diasDaSemana[indexDia]);
        resposta.put("quantidade", clinicaService.getEstatisticasSemana()[indexDia]);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/dia-menor")
    public ResponseEntity<Map<String, Object>> informarDiaMenor() {
        int indexDia = clinicaService.informarDiaComMenorQuantidadeAtendimentos();
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("diaComMenorAtendimento", diasDaSemana[indexDia]);
        resposta.put("quantidade", clinicaService.getEstatisticasSemana()[indexDia]);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/media")
    public ResponseEntity<Map<String, Object>> informarMedia() {
        double media = clinicaService.informarMediaAtendimentosPorDia();
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mediaDeAtendimentosPorDia", media);
        return ResponseEntity.ok(resposta);
    }
}
