package com.example.caritaspiapplication.model;

public class Atendido {

    private long cod_atend;
    private String nome;
    private String ra;
    private String nis;
    private String data_nasci;
    private String rua;
    private String bairro;
    private String n_casa;
    private Long cod_res;

    private String matriculas; // NOVO --------

    public Atendido() {
    }

    public long getCod_atend() {
        return cod_atend;
    }

    public void setCod_atend(long cod_atend) {
        this.cod_atend = cod_atend;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getData_nasci() {
        return data_nasci;
    }

    public void setData_nasci(String data_nasci) {
        this.data_nasci = data_nasci;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getN_casa() {
        return n_casa;
    }

    public void setN_casa(String n_casa) {
        this.n_casa = n_casa;
    }

    public Long getCod_res() {
        return cod_res;
    }

    public void setCod_res(Long cod_res) {
        this.cod_res = cod_res;
    }

    public String getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(String matriculas) {
        this.matriculas = matriculas;
    }

    @Override
    public String toString() {
        return this.nome + " - RA: " + this.getRa();
    }
}
