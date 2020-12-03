package com.example.caritaspiapplication.model;

public class Turma {

    private long cod_turma;
    private String nome;
    private String periodo;
    private long cod_inst;

    public Turma() {
    }

    public long getCod_turma() {
        return cod_turma;
    }

    public void setCod_turma(long cod_turma) {
        this.cod_turma = cod_turma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public long getCod_inst() {
        return cod_inst;
    }

    public void setCod_inst(long cod_inst) {
        this.cod_inst = cod_inst;
    }

    @Override
    public String toString() {
        return this.nome + "    -    Período: " + this.getPeriodo();
    }
}
