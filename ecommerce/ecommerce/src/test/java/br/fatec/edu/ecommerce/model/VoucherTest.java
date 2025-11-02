package br.fatec.edu.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {

    @Test
    void testConstrutorPadrao_DeveCriarVoucherVazio() {
        Voucher voucher = new Voucher();
        
        assertNotNull(voucher);
        assertNull(voucher.getType());
        assertEquals(0.0, voucher.getValue());
    }

    @Test
    void testConstrutorComParametros_DeveInicializarCorretamente() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 20.0);
        
        assertNotNull(voucher);
        assertEquals(Voucher.Type.PERCENTAGE, voucher.getType());
        assertEquals(20.0, voucher.getValue());
    }

    @Test
    void testConstrutorComFIXED_AMOUNT_DeveInicializarCorretamente() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 50.0);
        
        assertEquals(Voucher.Type.FIXED_AMOUNT, voucher.getType());
        assertEquals(50.0, voucher.getValue());
    }

    @Test
    void testSetType_DeveAtualizarTipo() {
        Voucher voucher = new Voucher();
        
        voucher.setType(Voucher.Type.PERCENTAGE);
        
        assertEquals(Voucher.Type.PERCENTAGE, voucher.getType());
    }

    @Test
    void testSetType_PERCENTAGEParaFIXED_AMOUNT_DeveAtualizar() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 10.0);
        
        voucher.setType(Voucher.Type.FIXED_AMOUNT);
        
        assertEquals(Voucher.Type.FIXED_AMOUNT, voucher.getType());
    }

    @Test
    void testSetType_FIXED_AMOUNTParaPERCENTAGE_DeveAtualizar() {
        Voucher voucher = new Voucher(Voucher.Type.FIXED_AMOUNT, 10.0);
        
        voucher.setType(Voucher.Type.PERCENTAGE);
        
        assertEquals(Voucher.Type.PERCENTAGE, voucher.getType());
    }

    @Test
    void testSetType_ComNull_DeveAceitarNull() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 10.0);
        
        voucher.setType(null);
        
        assertNull(voucher.getType());
    }

    @Test
    void testSetValue_DeveAtualizarValor() {
        Voucher voucher = new Voucher();
        
        voucher.setValue(25.5);
        
        assertEquals(25.5, voucher.getValue());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.01, 10.0, 50.0, 100.0, 200.0, -10.0, -1.0})
    void testSetValue_ComDiferentesValores_DeveAtualizarCorretamente(double value) {
        Voucher voucher = new Voucher();
        
        voucher.setValue(value);
        
        assertEquals(value, voucher.getValue());
    }

    @Test
    void testGetType_DeveRetornarTipoCorreto() {
        Voucher voucher1 = new Voucher(Voucher.Type.PERCENTAGE, 10.0);
        Voucher voucher2 = new Voucher(Voucher.Type.FIXED_AMOUNT, 20.0);
        
        assertEquals(Voucher.Type.PERCENTAGE, voucher1.getType());
        assertEquals(Voucher.Type.FIXED_AMOUNT, voucher2.getType());
    }

    @Test
    void testGetValue_DeveRetornarValorCorreto() {
        Voucher voucher = new Voucher(Voucher.Type.PERCENTAGE, 75.5);
        
        assertEquals(75.5, voucher.getValue());
    }

    @Test
    void testGettersESetters_Completamente() {
        Voucher voucher = new Voucher();
        
        voucher.setType(Voucher.Type.PERCENTAGE);
        voucher.setValue(30.0);
        
        assertEquals(Voucher.Type.PERCENTAGE, voucher.getType());
        assertEquals(30.0, voucher.getValue());
        
        voucher.setType(Voucher.Type.FIXED_AMOUNT);
        voucher.setValue(50.0);
        
        assertEquals(Voucher.Type.FIXED_AMOUNT, voucher.getType());
        assertEquals(50.0, voucher.getValue());
    }

    @Test
    void testTypeEnum_DeveTerPERCENTAGEEFIXED_AMOUNT() {
        assertNotNull(Voucher.Type.PERCENTAGE);
        assertNotNull(Voucher.Type.FIXED_AMOUNT);
        
        Voucher voucher1 = new Voucher(Voucher.Type.PERCENTAGE, 10.0);
        Voucher voucher2 = new Voucher(Voucher.Type.FIXED_AMOUNT, 10.0);
        
        assertTrue(voucher1.getType() == Voucher.Type.PERCENTAGE);
        assertTrue(voucher2.getType() == Voucher.Type.FIXED_AMOUNT);
    }

    @Test
    void testConstrutorEConstrutorPadrao_AmbosFuncionam() {
        Voucher voucher1 = new Voucher();
        Voucher voucher2 = new Voucher(Voucher.Type.PERCENTAGE, 15.0);
        
        assertNotNull(voucher1);
        assertNotNull(voucher2);
        assertEquals(Voucher.Type.PERCENTAGE, voucher2.getType());
        assertEquals(15.0, voucher2.getValue());
    }
}

