package com.agenciaalcateia.minhapizzaria.model;

public class Produto {

    private String nome;
    private String sabor;
    private String valor;
    private String precoP;
    private String precoM;
    private String precoG;
    private String tipo;

    public Produto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPrecoP() {
        return precoP;
    }

    public void setPrecoP(String precoP) {
        this.precoP = precoP;
    }

    public String getPrecoM() {
        return precoM;
    }

    public void setPrecoM(String precoM) {
        this.precoM = precoM;
    }

    public String getPrecoG() {
        return precoG;
    }

    public void setPrecoG(String precoG) {
        this.precoG = precoG;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
