package br.fatec.edu.ecommerce.model;

public class CartItem {

    private Double preco;
    private Integer quantidade;

    public CartItem(){}

    public CartItem(Double preco, Integer quantidade) {
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
