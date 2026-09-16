package com.clinica.service;

import com.clinica.model.Atendimento;
import com.clinica.model.Paciente;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ClinicaService {

    
    private List<Paciente> pacientes = new ArrayList<>();
    
    private Queue<Paciente> fila = new LinkedList<>();
    
    private Stack<Atendimento> historico = new Stack<>();
    
    private int[] atendimentosSemana = new int[7];
    
    private int[][] salas = {
        {0, 0},
        {0, 0},
        {0, 0}
    };
    
    private Long idPacienteCounter = 1L;
    private Long idAtendimentoCounter = 1L;

    
    public Paciente cadastrarPaciente(Paciente paciente) {
        paciente.setId(idPacienteCounter++);
        pacientes.add(paciente);
        return paciente;
    }

    public List<Paciente> listarPacientes() {
        return pacientes;
    }

        public Paciente buscarPacientePorId(Long id) {
        for (Paciente p : pacientes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null; 
    }

    public List<Paciente> ordenarPacientesPorNome() {
        List<Paciente> listaOrdenada = new ArrayList<>(pacientes);
        listaOrdenada.sort(Comparator.comparing(Paciente::getNome));
        return listaOrdenada;
    }

    public List<Paciente> ordenarPacientesPorIdade() {
        List<Paciente> listaOrdenada = new ArrayList<>(pacientes);
        listaOrdenada.sort(Comparator.comparingInt(Paciente::getIdade));
        return listaOrdenada;
    }

    public void adicionarPacienteFila(Long idPaciente) {
        Paciente p = buscarPacientePorId(idPaciente);
        if (p != null) {
            fila.add(p); 
        } else {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
    }

    public Paciente consultarProximoPaciente() {
        return fila.peek(); 
    }

    public Atendimento atenderPaciente() {
        Paciente p = fila.poll(); 
        if (p != null) {
            Atendimento atendimento = new Atendimento(idAtendimentoCounter++, p, LocalDateTime.now());
            historico.push(atendimento);
            
            int diaSemanaIndex = LocalDateTime.now().getDayOfWeek().getValue() % 7; 
            atendimentosSemana[diaSemanaIndex]++;
            
            return atendimento;
        }
        return null;
    }

    public List<Paciente> exibirFilaAtual() {
        return new ArrayList<>(fila);
    }

    public Atendimento consultarUltimoAtendimento() {
        if (historico.isEmpty()) {
            return null;
        }
        return historico.peek(); 
    }

    public Atendimento removerUltimoAtendimento() {
        if (historico.isEmpty()) {
            return null;
        }
        return historico.pop(); 
    }

    public List<Atendimento> exibirHistoricoCompleto() {
        List<Atendimento> copiaHistorico = new ArrayList<>(historico);
        Collections.reverse(copiaHistorico); 
        return copiaHistorico;
    }

    public int informarTotalPacientesAtendidos() {
        int total = 0;
        for (int qte : atendimentosSemana) {
            total += qte;
        }
        return total;
    }

    public int informarDiaComMaiorQuantidadeAtendimentos() {
        int maxIndex = 0;
        for (int i = 1; i < atendimentosSemana.length; i++) {
            if (atendimentosSemana[i] > atendimentosSemana[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex; 
    }

    public int informarDiaComMenorQuantidadeAtendimentos() {
        int minIndex = 0;
        for (int i = 1; i < atendimentosSemana.length; i++) {
            if (atendimentosSemana[i] < atendimentosSemana[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex; 
    }

    public double informarMediaAtendimentosPorDia() {
        int total = informarTotalPacientesAtendidos();
        return (double) total / atendimentosSemana.length;
    }
    
    public int[] getEstatisticasSemana() {
        return atendimentosSemana;
    }

    public int[][] exibirMatrizSalas() {
        return salas;
    }

    public List<String> listarSalasOcupadas() {
        List<String> ocupadas = new ArrayList<>();
        for (int i = 0; i < salas.length; i++) {
            for (int j = 0; j < salas[i].length; j++) {
                if (salas[i][j] == 1) {
                    int salaNumero = (i * 2) + j + 1;
                    ocupadas.add("Sala " + salaNumero);
                }
            }
        }
        return ocupadas;
    }

    public List<String> listarSalasDisponiveis() {
        List<String> disponiveis = new ArrayList<>();
        for (int i = 0; i < salas.length; i++) {
            for (int j = 0; j < salas[i].length; j++) {
                if (salas[i][j] == 0) {
                    int salaNumero = (i * 2) + j + 1;
                    disponiveis.add("Sala " + salaNumero);
                }
            }
        }
        return disponiveis;
    }

    public boolean alterarStatusSala(int linha, int coluna, int status) {
        if (linha >= 0 && linha < salas.length && coluna >= 0 && coluna < salas[linha].length) {
            if (status == 0 || status == 1) {
                salas[linha][coluna] = status;
                return true;
            }
        }
        return false;
    }
}
