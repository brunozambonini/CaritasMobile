package com.example.caritaspiapplication.model;

public class Instrutor {

    private long cod_inst;
    private String nome;
    private String rg;

    public Instrutor() {
    }

    public long getCod_inst() {
        return cod_inst;
    }

    public void setCod_inst(long cod_inst) {
        this.cod_inst = cod_inst;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public String toString() {
        return this.nome + " - RG: " + this.getRg();
    }
}
