package br.fatec.edu.ecommerce.validator;

import br.fatec.edu.ecommerce.model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CartItemValidatorTest {

    private CartItemValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CartItemValidator();
    }

    // ========== Testes para isValid ==========

    @Test
    void testIsValid_ComItemValido_DeveRetornarTrue() {
        CartItem item = new CartItem(10.0, 5);
        
        assertTrue(validator.isValid(item), "Item válido deve retornar true");
    }

    @Test
    void testIsValid_ComItemNulo_DeveRetornarFalse() {
        assertFalse(validator.isValid(null), "Item nulo deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testIsValid_ComPrecoNulo_DeveRetornarFalse(Double preco) {
        CartItem item = new CartItem(preco, 5);
        
        assertFalse(validator.isValid(item), "Item com preço nulo deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testIsValid_ComQuantidadeNula_DeveRetornarFalse(Integer quantidade) {
        CartItem item = new CartItem(10.0, quantidade);
        
        assertFalse(validator.isValid(item), "Item com quantidade nula deve retornar false");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, -1.0, 0.0})
    void testIsValid_ComPrecoInvalido_DeveRetornarFalse(double preco) {
        CartItem item = new CartItem(preco, 5);
        
        assertFalse(validator.isValid(item), "Item com preço inválido (" + preco + ") deve retornar false");
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 0})
    void testIsValid_ComQuantidadeInvalida_DeveRetornarFalse(int quantidade) {
        CartItem item = new CartItem(10.0, quantidade);
        
        assertFalse(validator.isValid(item), "Item com quantidade inválida (" + quantidade + ") deve retornar false");
    }

    // ========== Testes para hasValidPrice ==========

    @Test
    void testHasValidPrice_ComPrecoValido_DeveRetornarTrue() {
        CartItem item = new CartItem(15.5, 3);
        
        assertTrue(validator.hasValidPrice(item), "Preço válido deve retornar true");
    }

    @Test
    void testHasValidPrice_ComItemNulo_DeveRetornarFalse() {
        assertFalse(validator.hasValidPrice(null), "Item nulo deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testHasValidPrice_ComPrecoNulo_DeveRetornarFalse(Double preco) {
        CartItem item = new CartItem(preco, 3);
        
        assertFalse(validator.hasValidPrice(item), "Preço nulo deve retornar false");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-50.0, -1.0, 0.0})
    void testHasValidPrice_ComPrecoInvalido_DeveRetornarFalse(double preco) {
        CartItem item = new CartItem(preco, 3);
        
        assertFalse(validator.hasValidPrice(item), "Preço inválido (" + preco + ") deve retornar false");
    }

    // ========== Testes para hasValidQuantity ==========

    @Test
    void testHasValidQuantity_ComQuantidadeValida_DeveRetornarTrue() {
        CartItem item = new CartItem(10.0, 7);
        
        assertTrue(validator.hasValidQuantity(item), "Quantidade válida deve retornar true");
    }

    @Test
    void testHasValidQuantity_ComItemNulo_DeveRetornarFalse() {
        assertFalse(validator.hasValidQuantity(null), "Item nulo deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testHasValidQuantity_ComQuantidadeNula_DeveRetornarFalse(Integer quantidade) {
        CartItem item = new CartItem(10.0, quantidade);
        
        assertFalse(validator.hasValidQuantity(item), "Quantidade nula deve retornar false");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0})
    void testHasValidQuantity_ComQuantidadeInvalida_DeveRetornarFalse(int quantidade) {
        CartItem item = new CartItem(10.0, quantidade);
        
        assertFalse(validator.hasValidQuantity(item), "Quantidade inválida (" + quantidade + ") deve retornar false");
    }

    // ========== Testes para isPriceNotNull ==========

    @Test
    void testIsPriceNotNull_ComPrecoNaoNulo_DeveRetornarTrue() {
        CartItem item = new CartItem(20.0, 2);
        
        assertTrue(validator.isPriceNotNull(item), "Preço não nulo deve retornar true");
    }

    @Test
    void testIsPriceNotNull_ComItemNulo_DeveRetornarFalse() {
        assertFalse(validator.isPriceNotNull(null), "Item nulo deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testIsPriceNotNull_ComPrecoNulo_DeveRetornarFalse(Double preco) {
        CartItem item = new CartItem(preco, 2);
        
        assertFalse(validator.isPriceNotNull(item), "Preço nulo deve retornar false");
    }

    // ========== Testes para isQuantityNotNull ==========

    @Test
    void testIsQuantityNotNull_ComQuantidadeNaoNula_DeveRetornarTrue() {
        CartItem item = new CartItem(10.0, 4);
        
        assertTrue(validator.isQuantityNotNull(item), "Quantidade não nula deve retornar true");
    }

    @Test
    void testIsQuantityNotNull_ComItemNulo_DeveRetornarFalse() {
        assertFalse(validator.isQuantityNotNull(null), "Item nulo deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testIsQuantityNotNull_ComQuantidadeNula_DeveRetornarFalse(Integer quantidade) {
        CartItem item = new CartItem(10.0, quantidade);
        
        assertFalse(validator.isQuantityNotNull(item), "Quantidade nula deve retornar false");
    }

    // ========== Testes para isPricePositive ==========

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1.0, 10.5, 100.0, 1000.99})
    void testIsPricePositive_ComPrecoPositivo_DeveRetornarTrue(double preco) {
        CartItem item = new CartItem(preco, 1);
        
        assertTrue(validator.isPricePositive(item), "Preço positivo (" + preco + ") deve retornar true");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, -1.0, 0.0})
    void testIsPricePositive_ComPrecoNaoPositivo_DeveRetornarFalse(double preco) {
        CartItem item = new CartItem(preco, 1);
        
        assertFalse(validator.isPricePositive(item), "Preço não positivo (" + preco + ") deve retornar false");
    }

    @Test
    void testIsPricePositive_ComPrecoNulo_DeveRetornarFalse() {
        CartItem item = new CartItem(null, 1);
        
        assertFalse(validator.isPricePositive(item), "Preço nulo deve retornar false");
    }

    @Test
    void testIsPricePositive_ComItemNulo_DeveRetornarFalse() {
        assertFalse(validator.isPricePositive(null), "Item nulo deve retornar false");
    }

    // ========== Testes para isQuantityPositive ==========

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 100, 1000})
    void testIsQuantityPositive_ComQuantidadePositiva_DeveRetornarTrue(int quantidade) {
        CartItem item = new CartItem(10.0, quantidade);
        
        assertTrue(validator.isQuantityPositive(item), "Quantidade positiva (" + quantidade + ") deve retornar true");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0})
    void testIsQuantityPositive_ComQuantidadeNaoPositiva_DeveRetornarFalse(int quantidade) {
        CartItem item = new CartItem(10.0, quantidade);
        
        assertFalse(validator.isQuantityPositive(item), "Quantidade não positiva (" + quantidade + ") deve retornar false");
    }

    @Test
    void testIsQuantityPositive_ComQuantidadeNula_DeveRetornarFalse() {
        CartItem item = new CartItem(10.0, null);
        
        assertFalse(validator.isQuantityPositive(item), "Quantidade nula deve retornar false");
    }

    @Test
    void testIsQuantityPositive_ComItemNulo_DeveRetornarFalse() {
        assertFalse(validator.isQuantityPositive(null), "Item nulo deve retornar false");
    }

    // ========== Testes para hasValidSubtotal ==========

    @ParameterizedTest
    @MethodSource("provideValidItemsForSubtotal")
    void testHasValidSubtotal_ComItemValido_DeveRetornarTrue(double preco, int quantidade) {
        CartItem item = new CartItem(preco, quantidade);
        
        assertTrue(validator.hasValidSubtotal(item), 
            "Item com subtotal válido (preço: " + preco + ", quantidade: " + quantidade + ") deve retornar true");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidItemsForSubtotal")
    void testHasValidSubtotal_ComItemInvalido_DeveRetornarFalse(CartItem item, String descricao) {
        assertFalse(validator.hasValidSubtotal(item), 
            "Item com subtotal inválido (" + descricao + ") deve retornar false");
    }

    // ========== Métodos auxiliares para parâmetros ==========

    private static Stream<Arguments> provideValidItemsForSubtotal() {
        return Stream.of(
            Arguments.of(10.0, 1),
            Arguments.of(5.5, 2),
            Arguments.of(100.0, 10),
            Arguments.of(0.5, 100),
            Arguments.of(99.99, 5)
        );
    }

    private static Stream<Arguments> provideInvalidItemsForSubtotal() {
        return Stream.of(
            Arguments.of(null, "item nulo"),
            Arguments.of(new CartItem(null, 5), "preço nulo"),
            Arguments.of(new CartItem(10.0, null), "quantidade nula"),
            Arguments.of(new CartItem(-10.0, 5), "preço negativo"),
            Arguments.of(new CartItem(10.0, -5), "quantidade negativa"),
            Arguments.of(new CartItem(0.0, 5), "preço zero"),
            Arguments.of(new CartItem(10.0, 0), "quantidade zero")
        );
    }
}

