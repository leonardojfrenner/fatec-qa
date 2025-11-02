package br.fatec.edu.ecommerce.validator;

import br.fatec.edu.ecommerce.model.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VoucherValidatorTest {

    private VoucherValidator validator;

    @BeforeEach
    void setUp() {
        validator = new VoucherValidator();
    }

    // ========== Testes para isValid ==========

    @Test
    void testIsValid_ComVoucherValidoFIXED_AMOUNT_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 10.0);
        
        assertTrue(validator.isValid(voucher), "Voucher válido FIXED_AMOUNT deve retornar true");
    }

    @Test
    void testIsValid_ComVoucherValidoPERCENTAGE_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 15.0);
        
        assertTrue(validator.isValid(voucher), "Voucher válido PERCENTAGE deve retornar true");
    }

    @Test
    void testIsValid_ComVoucherNulo_DeveRetornarFalse() {
        assertFalse(validator.isValid(null), "Voucher nulo deve retornar false");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidVouchers")
    void testIsValid_ComVoucherInvalido_DeveRetornarFalse(Voucher voucher, String descricao) {
        assertFalse(validator.isValid(voucher), 
            "Voucher inválido (" + descricao + ") deve retornar false");
    }

    // ========== Testes para hasValidType ==========

    @Test
    void testHasValidType_ComTipoFIXED_AMOUNT_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 10.0);
        
        assertTrue(validator.hasValidType(voucher), "Tipo FIXED_AMOUNT deve retornar true");
    }

    @Test
    void testHasValidType_ComTipoPERCENTAGE_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 20.0);
        
        assertTrue(validator.hasValidType(voucher), "Tipo PERCENTAGE deve retornar true");
    }

    @Test
    void testHasValidType_ComVoucherNulo_DeveRetornarFalse() {
        assertFalse(validator.hasValidType(null), "Voucher nulo deve retornar false");
    }

    @Test
    void testHasValidType_ComTipoNulo_DeveRetornarFalse() {
        Voucher voucher = new Voucher();
        voucher.setType(null);
        voucher.setValue(10.0);
        
        assertFalse(validator.hasValidType(voucher), "Tipo nulo deve retornar false");
    }

    // ========== Testes para hasValidValue ==========

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.01, 1.0, 10.0, 50.0, 100.0, 1000.0})
    void testHasValidValue_ComValorValido_DeveRetornarTrue(double value) {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, value);
        
        assertTrue(validator.hasValidValue(voucher), 
            "Valor válido (" + value + ") deve retornar true");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -10.0, -1.0, -0.01})
    void testHasValidValue_ComValorNegativo_DeveRetornarFalse(double value) {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, value);
        
        assertFalse(validator.hasValidValue(voucher), 
            "Valor negativo (" + value + ") deve retornar false");
    }

    @Test
    void testHasValidValue_ComVoucherNulo_DeveRetornarFalse() {
        assertFalse(validator.hasValidValue(null), "Voucher nulo deve retornar false");
    }

    // ========== Testes para isFixedAmount ==========

    @Test
    void testIsFixedAmount_ComTipoFIXED_AMOUNT_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 15.0);
        
        assertTrue(validator.isFixedAmount(voucher), "Tipo FIXED_AMOUNT deve retornar true");
    }

    @Test
    void testIsFixedAmount_ComTipoPERCENTAGE_DeveRetornarFalse() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 15.0);
        
        assertFalse(validator.isFixedAmount(voucher), "Tipo PERCENTAGE deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testIsFixedAmount_ComTipoNulo_DeveRetornarFalse(Voucher.Type type) {
        Voucher voucher = new Voucher();
        voucher.setType(type);
        voucher.setValue(10.0);
        
        assertFalse(validator.isFixedAmount(voucher), "Tipo nulo deve retornar false");
    }

    @Test
    void testIsFixedAmount_ComVoucherNulo_DeveRetornarFalse() {
        assertFalse(validator.isFixedAmount(null), "Voucher nulo deve retornar false");
    }

    // ========== Testes para isPercentage ==========

    @Test
    void testIsPercentage_ComTipoPERCENTAGE_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 25.0);
        
        assertTrue(validator.isPercentage(voucher), "Tipo PERCENTAGE deve retornar true");
    }

    @Test
    void testIsPercentage_ComTipoFIXED_AMOUNT_DeveRetornarFalse() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 25.0);
        
        assertFalse(validator.isPercentage(voucher), "Tipo FIXED_AMOUNT deve retornar false");
    }

    @ParameterizedTest
    @NullSource
    void testIsPercentage_ComTipoNulo_DeveRetornarFalse(Voucher.Type type) {
        Voucher voucher = new Voucher();
        voucher.setType(type);
        voucher.setValue(10.0);
        
        assertFalse(validator.isPercentage(voucher), "Tipo nulo deve retornar false");
    }

    @Test
    void testIsPercentage_ComVoucherNulo_DeveRetornarFalse() {
        assertFalse(validator.isPercentage(null), "Voucher nulo deve retornar false");
    }

    // ========== Testes para hasValidPercentageValue ==========

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1.0, 10.0, 50.0, 99.0, 100.0})
    void testHasValidPercentageValue_ComPercentualValido_DeveRetornarTrue(double value) {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, value);
        
        assertTrue(validator.hasValidPercentageValue(voucher), 
            "Percentual válido (" + value + "%) deve retornar true");
    }

    @Test
    void testHasValidPercentageValue_ComValueExatamente100_DeveRetornarTrue() {
        // Teste específico para cobrir branch: value <= 100 quando value == 100
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 100.0);
        
        assertTrue(validator.hasValidPercentageValue(voucher), 
            "Percentual exatamente 100% deve retornar true");
    }

    @Test
    void testHasValidPercentageValue_ComValueExatamente0_DeveRetornarTrue() {
        // Teste específico para cobrir branch: value >= 0 quando value == 0
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 0.0);
        
        assertTrue(validator.hasValidPercentageValue(voucher), 
            "Percentual exatamente 0% deve retornar true");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, -1.0, 100.01, 101.0, 150.0, 200.0})
    void testHasValidPercentageValue_ComPercentualInvalido_DeveRetornarFalse(double value) {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, value);
        
        assertFalse(validator.hasValidPercentageValue(voucher), 
            "Percentual inválido (" + value + "%) deve retornar false");
    }

    @Test
    void testHasValidPercentageValue_ComTipoFIXED_AMOUNT_DeveRetornarFalse() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 50.0);
        
        assertFalse(validator.hasValidPercentageValue(voucher), 
            "Voucher FIXED_AMOUNT não deve ser validado como percentual");
    }

    @Test
    void testHasValidPercentageValue_ComTipoNulo_DeveRetornarFalse() {
        Voucher voucher = new Voucher();
        voucher.setType(null);
        voucher.setValue(50.0);
        
        assertFalse(validator.hasValidPercentageValue(voucher), "Tipo nulo deve retornar false");
    }

    // ========== Testes para hasPositiveFixedAmount ==========

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1.0, 10.0, 50.0, 100.0, 1000.0})
    void testHasPositiveFixedAmount_ComValorFixoPositivo_DeveRetornarTrue(double value) {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, value);
        
        assertTrue(validator.hasPositiveFixedAmount(voucher), 
            "Valor fixo positivo (" + value + ") deve retornar true");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, -1.0, 0.0})
    void testHasPositiveFixedAmount_ComValorFixoNaoPositivo_DeveRetornarFalse(double value) {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, value);
        
        assertFalse(validator.hasPositiveFixedAmount(voucher), 
            "Valor fixo não positivo (" + value + ") deve retornar false");
    }

    @Test
    void testHasPositiveFixedAmount_ComTipoPERCENTAGE_DeveRetornarFalse() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 50.0);
        
        assertFalse(validator.hasPositiveFixedAmount(voucher), 
            "Voucher PERCENTAGE não deve ser validado como valor fixo");
    }

    @Test
    void testHasPositiveFixedAmount_ComTipoNulo_DeveRetornarFalse() {
        Voucher voucher = new Voucher();
        voucher.setType(null);
        voucher.setValue(50.0);
        
        assertFalse(validator.hasPositiveFixedAmount(voucher), "Tipo nulo deve retornar false");
    }

    @Test
    void testHasPositiveFixedAmount_ComValorZero_DeveRetornarFalse() {
        // Teste específico para cobrir branch: value > 0 quando value == 0
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 0.0);
        
        assertFalse(validator.hasPositiveFixedAmount(voucher), 
            "Valor fixo zero deve retornar false");
    }

    @Test
    void testHasValidType_ComVoucherNull_DeveRetornarFalse() {
        // Teste específico para branch: voucher != null quando voucher == null
        assertFalse(validator.hasValidType(null), "Voucher nulo deve retornar false");
    }

    @Test
    void testHasValidType_ComTipoNull_DeveRetornarFalse() {
        // Teste específico para branch: voucher.getType() != null quando é null
        Voucher voucher = new Voucher();
        voucher.setType(null);
        
        assertFalse(validator.hasValidType(voucher), "Tipo nulo deve retornar false");
    }

    // ========== Testes para canBeApplied ==========

    @Test
    void testCanBeApplied_ComVoucherValidoComValorPositivo_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 10.0);
        
        assertTrue(validator.canBeApplied(voucher), 
            "Voucher válido com valor positivo deve retornar true");
    }

    @Test
    void testCanBeApplied_ComVoucherPercentualValido_DeveRetornarTrue() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 20.0);
        
        assertTrue(validator.canBeApplied(voucher), 
            "Voucher percentual válido deve retornar true");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, -1.0, 0.0})
    void testCanBeApplied_ComValorZeroOuNegativo_DeveRetornarFalse(double value) {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, value);
        
        assertFalse(validator.canBeApplied(voucher), 
            "Voucher com valor zero ou negativo (" + value + ") deve retornar false");
    }

    @Test
    void testCanBeApplied_ComVoucherNulo_DeveRetornarFalse() {
        assertFalse(validator.canBeApplied(null), "Voucher nulo deve retornar false");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidVouchers")
    void testCanBeApplied_ComVoucherInvalido_DeveRetornarFalse(Voucher voucher, String descricao) {
        assertFalse(validator.canBeApplied(voucher), 
            "Voucher inválido (" + descricao + ") não pode ser aplicado");
    }

    // ========== Métodos auxiliares para parâmetros ==========

    private static Stream<Arguments> provideInvalidVouchers() {
        return Stream.of(
            Arguments.of(new Voucher(null, 10.0), "tipo nulo"),
            Arguments.of(createVoucherWithNullType(), "tipo nulo (criado sem construtor)"),
            Arguments.of(new Voucher(Voucher.Type.FIXED_AMOUNT, -10.0), "valor negativo"),
            Arguments.of(new Voucher(Voucher.Type.PERCENTAGE, -5.0), "percentual negativo")
        );
    }

    private static Voucher createVoucherWithNullType() {
        Voucher voucher = new Voucher();
        voucher.setType(null);
        voucher.setValue(10.0);
        return voucher;
    }
}

