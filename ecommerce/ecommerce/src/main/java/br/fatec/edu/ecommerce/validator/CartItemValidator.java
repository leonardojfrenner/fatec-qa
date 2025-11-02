package br.fatec.edu.ecommerce.validator;

import br.fatec.edu.ecommerce.model.CartItem;

public class CartItemValidator {

    /**
     * Valida se o CartItem é válido
     * @param item CartItem a ser validado
     * @return true se o item for válido, false caso contrário
     */
    public boolean isValid(CartItem item) {
        if (item == null) {
            return false;
        }
        
        return hasValidPrice(item) && hasValidQuantity(item);
    }

    /**
     * Valida se o preço é válido (não nulo e maior que zero)
     * @param item CartItem a ser validado
     * @return true se o preço for válido, false caso contrário
     */
    public boolean hasValidPrice(CartItem item) {
        if (item == null) {
            return false;
        }
        
        Double preco = item.getPreco();
        return preco != null && preco > 0;
    }

    /**
     * Valida se a quantidade é válida (não nula e maior que zero)
     * @param item CartItem a ser validado
     * @return true se a quantidade for válida, false caso contrário
     */
    public boolean hasValidQuantity(CartItem item) {
        if (item == null) {
            return false;
        }
        
        Integer quantidade = item.getQuantidade();
        return quantidade != null && quantidade > 0;
    }

    /**
     * Valida se o preço não é nulo
     * @param item CartItem a ser validado
     * @return true se o preço não for nulo, false caso contrário
     */
    public boolean isPriceNotNull(CartItem item) {
        return item != null && item.getPreco() != null;
    }

    /**
     * Valida se a quantidade não é nula
     * @param item CartItem a ser validado
     * @return true se a quantidade não for nula, false caso contrário
     */
    public boolean isQuantityNotNull(CartItem item) {
        return item != null && item.getQuantidade() != null;
    }

    /**
     * Valida se o preço é positivo (maior que zero)
     * @param item CartItem a ser validado
     * @return true se o preço for positivo, false caso contrário
     */
    public boolean isPricePositive(CartItem item) {
        if (!isPriceNotNull(item)) {
            return false;
        }
        
        return item.getPreco() > 0;
    }

    /**
     * Valida se a quantidade é positiva (maior que zero)
     * @param item CartItem a ser validado
     * @return true se a quantidade for positiva, false caso contrário
     */
    public boolean isQuantityPositive(CartItem item) {
        if (!isQuantityNotNull(item)) {
            return false;
        }
        
        return item.getQuantidade() > 0;
    }

    /**
     * Valida se o subtotal (preço * quantidade) é válido e positivo
     * @param item CartItem a ser validado
     * @return true se o subtotal for válido e positivo, false caso contrário
     */
    public boolean hasValidSubtotal(CartItem item) {
        if (!isValid(item)) {
            return false;
        }
        
        double subtotal = item.getPreco() * item.getQuantidade();
        return subtotal > 0;
    }
}

