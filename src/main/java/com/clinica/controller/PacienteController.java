package com.clinica.controller;

import com.clinica.model.Paciente;
import com.clinica.service.ClinicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final ClinicaService clinicaService;

    public PacienteController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @PostMapping
    public ResponseEntity<Paciente> cadastrar(@RequestBody Paciente paciente) {
        Paciente novoPaciente = clinicaService.cadastrarPaciente(paciente);
        return ResponseEntity.ok(novoPaciente);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(clinicaService.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Paciente paciente = clinicaService.buscarPacientePorId(id);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ordenados/nome")
    public ResponseEntity<List<Paciente>> listarOrdenadosPorNome() {
        return ResponseEntity.ok(clinicaService.ordenarPacientesPorNome());
    }

    @GetMapping("/ordenados/idade")
    public ResponseEntity<List<Paciente>> listarOrdenadosPorIdade() {
        return ResponseEntity.ok(clinicaService.ordenarPacientesPorIdade());
    }
}
