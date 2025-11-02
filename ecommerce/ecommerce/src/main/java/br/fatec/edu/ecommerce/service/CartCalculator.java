package br.fatec.edu.ecommerce.service;

import br.fatec.edu.ecommerce.model.CartItem;
import br.fatec.edu.ecommerce.model.Voucher;

import java.util.List;

public class CartCalculator {

    public Double calculateTotal(List<CartItem> items, Voucher voucher){
        // Validação: lançar IllegalArgumentException se items for nulo
        if (items == null) {
            throw new IllegalArgumentException("A lista de itens não pode ser nula");
        }
        
        // Calcular subtotal: soma de price * quantity de todos os itens
        double subtotal = 0.0;
        for (CartItem item : items) {
            if (item != null && item.getPreco() != null && item.getQuantidade() != null) {
                subtotal += item.getPreco() * item.getQuantidade();
            }
        }
        
        // Se o voucher não for nulo, aplicar desconto
        if (voucher != null) {
            double desconto = 0.0;
            
            if (voucher.getType() == Voucher.Type.FIXED_AMOUNT) {
                // FIXED_AMOUNT: subtrair value do subtotal
                desconto = voucher.getValue();
            } else if (voucher.getType() == Voucher.Type.PERCENTAGE) {
                // PERCENTAGE: calcular desconto (subtotal * value / 100)
                desconto = subtotal * voucher.getValue() / 100.0;
            }
            
            subtotal -= desconto;
        }
        
        // O total final nunca pode ser menor que zero
        // Se o desconto for maior que o subtotal, o total deve ser zero
        return Math.max(0.0, subtotal);
    }
}
