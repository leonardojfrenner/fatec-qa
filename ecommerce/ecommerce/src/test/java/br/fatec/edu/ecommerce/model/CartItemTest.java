package br.fatec.edu.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void testConstrutorPadrao_DeveCriarItemVazio() {
        CartItem item = new CartItem();
        
        assertNotNull(item);
        assertNull(item.getPreco());
        assertNull(item.getQuantidade());
    }

    @Test
    void testConstrutorComParametros_DeveInicializarCorretamente() {
        CartItem item = new CartItem(15.5, 3);
        
        assertNotNull(item);
        assertEquals(15.5, item.getPreco());
        assertEquals(3, item.getQuantidade());
    }

    @Test
    void testSetPreco_DeveAtualizarPreco() {
        CartItem item = new CartItem();
        
        item.setPreco(25.99);
        
        assertEquals(25.99, item.getPreco());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.01, 10.0, 100.0, 999.99})
    void testSetPreco_ComDiferentesValores_DeveAtualizarCorretamente(double preco) {
        CartItem item = new CartItem();
        
        item.setPreco(preco);
        
        assertEquals(preco, item.getPreco());
    }

    @Test
    void testSetPreco_ComNull_DeveAceitarNull() {
        CartItem item = new CartItem(10.0, 1);
        
        item.setPreco(null);
        
        assertNull(item.getPreco());
    }

    @Test
    void testSetPreco_ComValorNegativo_DeveAceitar() {
        CartItem item = new CartItem();
        
        item.setPreco(-10.0);
        
        assertEquals(-10.0, item.getPreco());
    }

    @Test
    void testSetQuantidade_DeveAtualizarQuantidade() {
        CartItem item = new CartItem();
        
        item.setQuantidade(5);
        
        assertEquals(5, item.getQuantidade());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 10, 100, 1000})
    void testSetQuantidade_ComDiferentesValores_DeveAtualizarCorretamente(int quantidade) {
        CartItem item = new CartItem();
        
        item.setQuantidade(quantidade);
        
        assertEquals(quantidade, item.getQuantidade());
    }

    @Test
    void testSetQuantidade_ComNull_DeveAceitarNull() {
        CartItem item = new CartItem(10.0, 1);
        
        item.setQuantidade(null);
        
        assertNull(item.getQuantidade());
    }

    @Test
    void testSetQuantidade_ComValorNegativo_DeveAceitar() {
        CartItem item = new CartItem();
        
        item.setQuantidade(-5);
        
        assertEquals(-5, item.getQuantidade());
    }

    @Test
    void testGettersESetters_Completamente() {
        CartItem item = new CartItem();
        
        item.setPreco(50.0);
        item.setQuantidade(4);
        
        assertEquals(50.0, item.getPreco());
        assertEquals(4, item.getQuantidade());
        
        item.setPreco(75.5);
        item.setQuantidade(2);
        
        assertEquals(75.5, item.getPreco());
        assertEquals(2, item.getQuantidade());
    }

    @Test
    void testConstrutorEConstrutorPadrao_AmbosFuncionam() {
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem(10.0, 2);
        
        assertNotNull(item1);
        assertNotNull(item2);
        assertEquals(10.0, item2.getPreco());
        assertEquals(2, item2.getQuantidade());
    }
}

