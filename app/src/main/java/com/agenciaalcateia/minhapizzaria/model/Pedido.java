package com.agenciaalcateia.minhapizzaria.model;

import java.util.ArrayList;

public class Pedido {

    private String produtos;
    private String valor;

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
}
