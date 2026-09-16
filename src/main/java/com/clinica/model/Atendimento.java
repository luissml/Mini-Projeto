package com.clinica.model;

import java.time.LocalDateTime;

public class Atendimento {

    private Long id;
    private Paciente paciente;
    private LocalDateTime dataHora;

    public Atendimento() {
    }

    public Atendimento(Long id, Paciente paciente, LocalDateTime dataHora) {
        this.id = id;
        this.paciente = paciente;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
