package br.fatec.edu.ecommerce.service;

import br.fatec.edu.ecommerce.model.CartItem;
import br.fatec.edu.ecommerce.model.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
}

