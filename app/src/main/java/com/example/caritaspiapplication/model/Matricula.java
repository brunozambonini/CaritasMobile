package com.example.caritaspiapplication.model;

public class Matricula {

    private long id;
    private long cod_turma;
    private long cod_atend;
    private String nome_turma;
    private String nome_atend;

    public Matricula() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCod_turma() {
        return cod_turma;
    }

    public void setCod_turma(long cod_turma) {
        this.cod_turma = cod_turma;
    }

    public long getCod_atend() {
        return cod_atend;
    }

    public void setCod_atend(long cod_atend) {
        this.cod_atend = cod_atend;
    }

    public String getNome_atend() {
        return nome_atend;
    }

    public void setNome_atend(String nome_atend) {
        this.nome_atend = nome_atend;
    }

    public String getNome_turma() {
        return nome_turma;
    }

    public void setNome_turma(String nome_turma) {
        this.nome_turma = nome_turma;
    }
}
