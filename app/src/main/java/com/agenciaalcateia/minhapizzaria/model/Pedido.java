package com.agenciaalcateia.minhapizzaria.model;

public class Pedido {

    private String produtos;
    private String valor;
    private String data;

    public Pedido() {
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
