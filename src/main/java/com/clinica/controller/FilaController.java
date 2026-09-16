package com.clinica.controller;

import com.clinica.model.Atendimento;
import com.clinica.model.Paciente;
import com.clinica.service.ClinicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fila")
public class FilaController {

    private final ClinicaService clinicaService;

    public FilaController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @PostMapping("/{idPaciente}")
    public ResponseEntity<String> adicionarNaFila(@PathVariable Long idPaciente) {
        try {
            clinicaService.adicionarPacienteFila(idPaciente);
            return ResponseEntity.ok("Paciente adicionado à fila com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/proximo")
    public ResponseEntity<Paciente> consultarProximo() {
        Paciente proximo = clinicaService.consultarProximoPaciente();
        if (proximo != null) {
            return ResponseEntity.ok(proximo);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/atender")
    public ResponseEntity<Atendimento> atenderPaciente() {
        Atendimento atendimento = clinicaService.atenderPaciente();
        if (atendimento != null) {
            return ResponseEntity.ok(atendimento);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> exibirFila() {
        return ResponseEntity.ok(clinicaService.exibirFilaAtual());
    }
}
