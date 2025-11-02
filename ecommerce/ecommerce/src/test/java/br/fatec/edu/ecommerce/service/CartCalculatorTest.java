package br.fatec.edu.ecommerce.service;

import br.fatec.edu.ecommerce.model.CartItem;
import br.fatec.edu.ecommerce.model.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CartCalculatorTest {

    private CartCalculator cartCalculator;

    @BeforeEach
    void setUp() {
        cartCalculator = new CartCalculator();
    }

    @Test
    void testCalculateTotal_ComListaDeItens_SemVoucher() {
        // Teste o cálculo com lista de itens, mas sem voucher (voucher nulo)
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        items.add(new CartItem(15.0, 3)); // 45.0
        items.add(new CartItem(5.0, 1));  // 5.0
        // Total esperado: 70.0

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertEquals(70.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_ComVoucherValorFixo_ResultadoPositivo() {
        // Teste com voucher de valor fixo que resulta em total positivo
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        items.add(new CartItem(15.0, 3)); // 45.0
        // Subtotal: 65.0
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 15.0);
        // Total esperado: 65.0 - 15.0 = 50.0

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(50.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_ComVoucherPercentual() {
        // Teste com voucher percentual
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        items.add(new CartItem(15.0, 3)); // 45.0
        // Subtotal: 65.0
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 20.0); // 20% de desconto
        // Desconto: 65.0 * 20 / 100 = 13.0
        // Total esperado: 65.0 - 13.0 = 52.0

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(52.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_DescontoFixoMaiorQueSubtotal_ResultadoZero() {
        // Teste caso de borda onde o desconto fixo é maior que o subtotal → resultado deve ser 0
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 1)); // 10.0
        // Subtotal: 10.0
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 50.0);
        // Desconto maior que subtotal, resultado deve ser 0

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(0.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_ListaVazia_ResultadoZero() {
        // Teste lista de itens vazia → resultado deve ser 0
        List<CartItem> items = new ArrayList<>();

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertEquals(0.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_ListaNula_LancaIllegalArgumentException() {
        // Teste lista de itens nula → deve lançar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            cartCalculator.calculateTotal(null, null);
        });
    }

    @Test
    void testCalculateTotal_VoucherPercentual100Porcento_ResultadoZero() {
        // Teste adicional: voucher percentual de 100% → resultado deve ser 0
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 100.0);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(0.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_VoucherPercentualMaiorQue100_ResultadoZero() {
        // Teste adicional: voucher percentual maior que 100% → resultado deve ser 0
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 150.0);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(0.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_ComItemNulo_NaoLancaException() {
        // Teste adicional: lista com item nulo não deve lançar exceção
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2));
        items.add(null);
        items.add(new CartItem(5.0, 1));

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertEquals(25.0, resultado, 0.01); // 10*2 + 5*1 = 25
    }

    @Test
    void testCalculateTotal_ComItemPrecoNulo_DeveIgnorarItem() {
        // Teste para branch: item.getPreco() == null
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        CartItem itemComPrecoNulo = new CartItem();
        itemComPrecoNulo.setPreco(null);
        itemComPrecoNulo.setQuantidade(5);
        items.add(itemComPrecoNulo); // deve ser ignorado
        items.add(new CartItem(5.0, 1)); // 5.0

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertEquals(25.0, resultado, 0.01); // 20 + 5 = 25 (item com preço nulo ignorado)
    }

    @Test
    void testCalculateTotal_ComItemQuantidadeNula_DeveIgnorarItem() {
        // Teste para branch: item.getQuantidade() == null
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        CartItem itemComQuantidadeNula = new CartItem();
        itemComQuantidadeNula.setPreco(15.0);
        itemComQuantidadeNula.setQuantidade(null);
        items.add(itemComQuantidadeNula); // deve ser ignorado
        items.add(new CartItem(5.0, 1)); // 5.0

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertEquals(25.0, resultado, 0.01); // 20 + 5 = 25 (item com quantidade nula ignorado)
    }

    @Test
    void testCalculateTotal_ComItemPrecoEQuantidadeNulos_DeveIgnorarItem() {
        // Teste para branch: ambos nulos
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2)); // 20.0
        CartItem itemNulo = new CartItem();
        itemNulo.setPreco(null);
        itemNulo.setQuantidade(null);
        items.add(itemNulo); // deve ser ignorado
        items.add(new CartItem(5.0, 1)); // 5.0

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertEquals(25.0, resultado, 0.01); // 20 + 5 = 25
    }

    @Test
    void testCalculateTotal_ComVoucherTipoNull_DeveIgnorarDesconto() {
        // Teste para branch: voucher.getType() == null (caso else implícito)
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(100.0, 1)); // Subtotal: 100.0
        
        Voucher voucher = new Voucher();
        voucher.setType(null); // Tipo nulo
        voucher.setValue(20.0);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        // Quando o tipo é null, nenhum desconto é aplicado
        assertEquals(100.0, resultado, 0.01);
    }

    @Test
    void testCalculateTotal_ComVoucherTipoNull_ComValor_DeveIgnorarDesconto() {
        // Teste adicional para garantir que voucher com tipo null não aplica desconto
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(50.0, 2)); // Subtotal: 100.0
        
        Voucher voucher = new Voucher();
        voucher.setType(null);
        voucher.setValue(50.0); // Valor alto, mas não deve aplicar

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(100.0, resultado, 0.01);
    }

    // ========== Testes adicionais com JUnit Params e assertTrue/assertFalse ==========

    @ParameterizedTest
    @MethodSource("provideValidCartScenarios")
    void testCalculateTotal_ResultadoNuncaNegativo_DeveSerTrue(double preco1, int qtd1, double preco2, int qtd2, 
                                                                Voucher.Type voucherType, double voucherValue) {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(preco1, qtd1));
        items.add(new CartItem(preco2, qtd2));
        Voucher voucher = new Voucher(voucherType, voucherValue);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertTrue(resultado >= 0, 
            "O resultado nunca deve ser negativo. Resultado: " + resultado);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 150.0, 200.0, 500.0, 1000.0})
    void testCalculateTotal_DescontoMaiorQueSubtotal_ResultadoDeveSerZero(double desconto) {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 1)); // Subtotal: 10.0
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, desconto);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertTrue(resultado == 0.0, 
            "Quando desconto (" + desconto + ") é maior que subtotal (10.0), resultado deve ser 0. Resultado: " + resultado);
    }

    @Test
    void testCalculateTotal_ListaVaziaComVoucher_ResultadoDeveSerZero() {
        List<CartItem> items = new ArrayList<>();
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 10.0);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertTrue(resultado == 0.0, "Lista vazia com voucher deve resultar em 0");
    }

    @ParameterizedTest
    @MethodSource("providePercentageVoucherScenarios")
    void testCalculateTotal_ComVoucherPercentual_ResultadoCorreto(double subtotal, double percentual, 
                                                                     double esperado) {
        List<CartItem> items = new ArrayList<>();
        // Criar itens que somam o subtotal desejado
        if (subtotal > 0) {
            items.add(new CartItem(subtotal, 1));
        }
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, percentual);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(esperado, resultado, 0.01, 
            "Subtotal: " + subtotal + ", Percentual: " + percentual + "%, Esperado: " + esperado);
    }

    @ParameterizedTest
    @MethodSource("provideFixedAmountVoucherScenarios")
    void testCalculateTotal_ComVoucherFixo_ResultadoCorreto(double subtotal, double desconto, 
                                                             double esperado) {
        List<CartItem> items = new ArrayList<>();
        if (subtotal > 0) {
            items.add(new CartItem(subtotal, 1));
        }
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, desconto);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertEquals(esperado, resultado, 0.01, 
            "Subtotal: " + subtotal + ", Desconto: " + desconto + ", Esperado: " + esperado);
        
        // Verificação adicional com assertTrue
        assertTrue(resultado >= 0, "Resultado não pode ser negativo");
    }

    @Test
    void testCalculateTotal_SemVoucher_ResultadoMaiorQueZeroQuandoTemItens() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(10.0, 2));
        items.add(new CartItem(5.0, 3));

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertTrue(resultado > 0, "Sem voucher e com itens, resultado deve ser maior que zero");
        assertEquals(35.0, resultado, 0.01);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 50.0, 99.0, 100.0})
    void testCalculateTotal_VoucherPercentualAte100_ResultadoNaoNegativo(double percentual) {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(100.0, 1)); // Subtotal: 100.0
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, percentual);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertTrue(resultado >= 0, 
            "Voucher percentual (" + percentual + "%) não deve resultar em valor negativo. Resultado: " + resultado);
    }

    @ParameterizedTest
    @ValueSource(doubles = {101.0, 150.0, 200.0, 500.0})
    void testCalculateTotal_VoucherPercentualMaiorQue100_ResultadoZero(double percentual) {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(100.0, 1)); // Subtotal: 100.0
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, percentual);

        Double resultado = cartCalculator.calculateTotal(items, voucher);

        assertTrue(resultado == 0.0, 
            "Voucher percentual maior que 100% (" + percentual + "%) deve resultar em 0. Resultado: " + resultado);
    }

    @Test
    void testCalculateTotal_ComMuitosItens_ResultadoSomaCorretamente() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(1.0, 1));
        items.add(new CartItem(2.0, 2));
        items.add(new CartItem(3.0, 3));
        items.add(new CartItem(4.0, 4));
        items.add(new CartItem(5.0, 5));
        // Subtotal: 1 + 4 + 9 + 16 + 25 = 55.0

        Double resultado = cartCalculator.calculateTotal(items, null);

        assertEquals(55.0, resultado, 0.01);
        assertTrue(resultado > 0, "Resultado deve ser positivo");
    }

    // ========== Métodos auxiliares para parâmetros ==========

    private static Stream<Arguments> provideValidCartScenarios() {
        return Stream.of(
            Arguments.of(10.0, 2, 15.0, 3, Voucher.Type.FIXED_AMOUNT, 5.0),
            Arguments.of(20.0, 1, 30.0, 2, Voucher.Type.PERCENTAGE, 10.0),
            Arguments.of(50.0, 1, 50.0, 1, Voucher.Type.FIXED_AMOUNT, 50.0),
            Arguments.of(100.0, 1, 0.0, 1, Voucher.Type.PERCENTAGE, 50.0),
            Arguments.of(10.0, 1, 10.0, 1, Voucher.Type.FIXED_AMOUNT, 100.0) // desconto maior que subtotal
        );
    }

    private static Stream<Arguments> providePercentageVoucherScenarios() {
        return Stream.of(
            Arguments.of(100.0, 0.0, 100.0),    // 0% desconto
            Arguments.of(100.0, 10.0, 90.0),   // 10% desconto
            Arguments.of(100.0, 25.0, 75.0),   // 25% desconto
            Arguments.of(100.0, 50.0, 50.0),   // 50% desconto
            Arguments.of(100.0, 100.0, 0.0),    // 100% desconto
            Arguments.of(100.0, 150.0, 0.0),    // 150% desconto (deve ser 0)
            Arguments.of(200.0, 30.0, 140.0),  // 200 com 30% = 140
            Arguments.of(50.0, 20.0, 40.0)      // 50 com 20% = 40
        );
    }

    private static Stream<Arguments> provideFixedAmountVoucherScenarios() {
        return Stream.of(
            Arguments.of(100.0, 0.0, 100.0),   // sem desconto
            Arguments.of(100.0, 10.0, 90.0),   // 10 de desconto
            Arguments.of(100.0, 50.0, 50.0),    // 50 de desconto
            Arguments.of(100.0, 100.0, 0.0),    // desconto igual ao subtotal
            Arguments.of(100.0, 150.0, 0.0),   // desconto maior (deve ser 0)
            Arguments.of(200.0, 75.0, 125.0),  // 200 com 75 de desconto = 125
            Arguments.of(50.0, 25.0, 25.0)      // 50 com 25 de desconto = 25
        );
    }
}

