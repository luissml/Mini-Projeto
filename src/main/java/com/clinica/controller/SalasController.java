package com.clinica.controller;

import com.clinica.service.ClinicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalasController {

    private final ClinicaService clinicaService;

    public SalasController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @GetMapping
    public ResponseEntity<int[][]> exibirMatriz() {
        return ResponseEntity.ok(clinicaService.exibirMatrizSalas());
    }

    @GetMapping("/ocupadas")
    public ResponseEntity<List<String>> listarOcupadas() {
        return ResponseEntity.ok(clinicaService.listarSalasOcupadas());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<String>> listarDisponiveis() {
        return ResponseEntity.ok(clinicaService.listarSalasDisponiveis());
    }

    @PutMapping("/{linha}/{coluna}")
    public ResponseEntity<String> alterarStatus(@PathVariable int linha,
                                                @PathVariable int coluna,
                                                @RequestParam int status) {
        boolean sucesso = clinicaService.alterarStatusSala(linha, coluna, status);
        if (sucesso) {
            return ResponseEntity.ok("Status da sala alterado com sucesso.");
        }
        return ResponseEntity.badRequest().body("Posição inválida ou status incorreto. Use 0 ou 1.");
    }

    @PutMapping("/{salaNumero}")
    public ResponseEntity<String> alterarStatusPorNumero(@PathVariable int salaNumero,
                                                         @RequestParam int status) {
        if (salaNumero < 1 || salaNumero > 6) {
            return ResponseEntity.badRequest().body("Número de sala inválido. Use de 1 a 6.");
        }
        int linha = (salaNumero - 1) / 2;
        int coluna = (salaNumero - 1) % 2;
        boolean sucesso = clinicaService.alterarStatusSala(linha, coluna, status);
        if (sucesso) {
            return ResponseEntity.ok("Status da sala alterado com sucesso.");
        }
        return ResponseEntity.badRequest().body("Status incorreto. Use 0 ou 1.");
    }
}
