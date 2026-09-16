package com.clinica.controller;

import com.clinica.model.Atendimento;
import com.clinica.service.ClinicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    private final ClinicaService clinicaService;

    public HistoricoController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @GetMapping("/ultimo")
    public ResponseEntity<Atendimento> consultarUltimo() {
        Atendimento atendimento = clinicaService.consultarUltimoAtendimento();
        if (atendimento != null) {
            return ResponseEntity.ok(atendimento);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/ultimo")
    public ResponseEntity<Atendimento> removerUltimo() {
        Atendimento atendimento = clinicaService.removerUltimoAtendimento();
        if (atendimento != null) {
            return ResponseEntity.ok(atendimento);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> exibirHistorico() {
        return ResponseEntity.ok(clinicaService.exibirHistoricoCompleto());
    }
}
