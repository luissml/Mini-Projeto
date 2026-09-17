package com.clinica.controller;

import com.clinica.model.Paciente;
import com.clinica.service.ClinicaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClinicaWebController {

    private final ClinicaService clinicaService;

    public ClinicaWebController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("qtdPacientes", clinicaService.listarPacientes().size());
        model.addAttribute("qtdFila", clinicaService.exibirFilaAtual().size());
        model.addAttribute("ultimoAtendimento", clinicaService.consultarUltimoAtendimento());
        model.addAttribute("salasOcupadas", clinicaService.listarSalasOcupadas().size());
        model.addAttribute("salasDisponiveis", clinicaService.listarSalasDisponiveis().size());
        return "index";
    }

    @GetMapping("/web/pacientes")
    public String pacientesPage(
            @RequestParam(required = false) Long idBusca,
            @RequestParam(required = false) String ordem,
            Model model) {

        List<Paciente> listaParaExibir;

        if (idBusca != null) {
            Paciente p = clinicaService.buscarPacientePorId(idBusca);
            listaParaExibir = new ArrayList<>();
            if (p != null) {
                listaParaExibir.add(p);
            }
        } 
        else if ("nome".equals(ordem)) {
            listaParaExibir = clinicaService.ordenarPacientesPorNome();
        } else if ("idade".equals(ordem)) {
            listaParaExibir = clinicaService.ordenarPacientesPorIdade();
        } 
        else {
            listaParaExibir = clinicaService.listarPacientes();
        }

        model.addAttribute("pacientes", listaParaExibir);
        
        model.addAttribute("novoPaciente", new Paciente());
        
        return "pacientes";
    }

    @PostMapping("/web/pacientes/cadastrar")
    public String cadastrarPaciente(Paciente paciente) {
        clinicaService.cadastrarPaciente(paciente);
        return "redirect:/web/pacientes"; 
    }

    @GetMapping("/web/fila")
    public String filaPage(Model model) {
        model.addAttribute("fila", clinicaService.exibirFilaAtual());
        model.addAttribute("proximo", clinicaService.consultarProximoPaciente());
        model.addAttribute("pacientesDisponiveis", clinicaService.listarPacientes());
        return "fila";
    }

    @PostMapping("/web/fila/adicionar")
    public String adicionarFila(@RequestParam Long idPaciente) {
        try {
            clinicaService.adicionarPacienteFila(idPaciente);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/web/fila";
    }

    @PostMapping("/web/fila/atender")
    public String atenderFila() {
        clinicaService.atenderPaciente();
        return "redirect:/web/fila";
    }

    @GetMapping("/web/historico")
    public String historicoPage(Model model) {
        model.addAttribute("historicoCompleto", clinicaService.exibirHistoricoCompleto());
        model.addAttribute("ultimoAtendimento", clinicaService.consultarUltimoAtendimento());
        return "historico";
    }

    @PostMapping("/web/historico/removerUltimo")
    public String removerUltimoHistorico() {
        clinicaService.removerUltimoAtendimento();
        return "redirect:/web/historico";
    }

    @GetMapping("/web/estatisticas")
    public String estatisticasPage(Model model) {
        String[] diasDaSemana = {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};
        
        model.addAttribute("total", clinicaService.informarTotalPacientesAtendidos());
        model.addAttribute("media", clinicaService.informarMediaAtendimentosPorDia());
        
        int diaMaior = clinicaService.informarDiaComMaiorQuantidadeAtendimentos();
        int diaMenor = clinicaService.informarDiaComMenorQuantidadeAtendimentos();
        
        model.addAttribute("diaMaiorNome", diasDaSemana[diaMaior]);
        model.addAttribute("diaMaiorQtd", clinicaService.getEstatisticasSemana()[diaMaior]);
        
        model.addAttribute("diaMenorNome", diasDaSemana[diaMenor]);
        model.addAttribute("diaMenorQtd", clinicaService.getEstatisticasSemana()[diaMenor]);
        
        return "estatisticas";
    }

    @GetMapping("/web/salas")
    public String salasPage(Model model) {
        model.addAttribute("matrizSalas", clinicaService.exibirMatrizSalas());
        return "salas";
    }

    @PostMapping("/web/salas/alterar")
    public String alterarSala(@RequestParam int salaNumero, @RequestParam int status) {
        int linha = (salaNumero - 1) / 2;
        int coluna = (salaNumero - 1) % 2;
        
        clinicaService.alterarStatusSala(linha, coluna, status);
        return "redirect:/web/salas";
    }
}
