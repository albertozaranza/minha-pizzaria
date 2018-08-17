package com.agenciaalcateia.minhapizzaria.model;

public class Carrinho {

    private String produdo;
    private String quantidade;
    private String valor;
    private String key;

    public Carrinho() {
    }

    public String getProdudo() {
        return produdo;
    }

    public void setProdudo(String produdo) {
        this.produdo = produdo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
