package br.fatec.edu.ecommerce.controllers;

import br.fatec.edu.ecommerce.model.CartItem;
import br.fatec.edu.ecommerce.model.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    private CartController cartController;
    private Model model;

    @BeforeEach
    void setUp() {
        cartController = new CartController();
        model = mock(Model.class);
    }

    @Test
    void testShowCart_DeveRetornarViewCart() {
        String viewName = cartController.showCart(model);
        
        assertEquals("cart", viewName);
        verify(model).addAttribute(eq("items"), any());
        verify(model).addAttribute(eq("cartItem"), any(CartItem.class));
        verify(model).addAttribute(eq("voucher"), any(Voucher.class));
        verify(model).addAttribute(eq("total"), any(Double.class));
    }

    @Test
    void testShowCart_ComItensNoCarrinho_DeveCalcularTotal() {
        // Adicionar itens ao carrinho
        CartItem item1 = new CartItem(10.0, 2);
        CartItem item2 = new CartItem(5.0, 3);
        cartController.addItem(item1);
        cartController.addItem(item2);
        
        String viewName = cartController.showCart(model);
        
        assertEquals("cart", viewName);
        verify(model, atLeastOnce()).addAttribute(eq("total"), eq(35.0));
    }

    @Test
    void testAddItem_DeveAdicionarItemAoCarrinho() {
        CartItem item = new CartItem(15.0, 4);
        
        assertEquals("redirect:/cart", cartController.addItem(item));
        
        cartController.showCart(model);
        verify(model).addAttribute(eq("items"), argThat(items -> 
            ((java.util.List<?>) items).size() == 1
        ));
    }

    @Test
    void testAddItem_ComMultiplosItens_DeveAdicionarTodos() {
        CartItem item1 = new CartItem(10.0, 2);
        CartItem item2 = new CartItem(20.0, 1);
        CartItem item3 = new CartItem(5.0, 5);
        
        cartController.addItem(item1);
        cartController.addItem(item2);
        cartController.addItem(item3);
        
        cartController.showCart(model);
        
        verify(model).addAttribute(eq("items"), argThat(items -> 
            ((java.util.List<?>) items).size() == 3
        ));
    }

    @Test
    void testCalculateTotal_ComVoucherPERCENTAGE_DeveAplicarDesconto() {
        // Adicionar itens
        cartController.addItem(new CartItem(100.0, 1)); // Subtotal: 100.0
        
        assertEquals("cart", cartController.calculateTotal("PERCENTAGE", 20.0, model));
        verify(model).addAttribute(eq("total"), eq(80.0)); // 100 - 20% = 80
        verify(model).addAttribute(eq("appliedVoucher"), any(Voucher.class));
    }

    @Test
    void testCalculateTotal_ComVoucherFIXED_AMOUNT_DeveAplicarDesconto() {
        cartController.addItem(new CartItem(100.0, 1)); // Subtotal: 100.0
        
        String viewName = cartController.calculateTotal("FIXED_AMOUNT", 30.0, model);
        
        assertEquals("cart", viewName);
        verify(model).addAttribute(eq("total"), eq(70.0)); // 100 - 30 = 70
        verify(model).addAttribute(eq("appliedVoucher"), any(Voucher.class));
    }

    @Test
    void testCalculateTotal_ComVoucherNulo_DeveIgnorarDesconto() {
        cartController.addItem(new CartItem(50.0, 2)); // Subtotal: 100.0
        
        String viewName = cartController.calculateTotal(null, null, model);
        
        assertEquals("cart", viewName);
        verify(model).addAttribute(eq("total"), eq(100.0));
        verify(model).addAttribute(eq("appliedVoucher"), isNull());
    }

    @Test
    void testCalculateTotal_ComVoucherVazio_DeveIgnorarDesconto() {
        cartController.addItem(new CartItem(50.0, 2));
        
        cartController.calculateTotal("", 10.0, model);
        
        verify(model).addAttribute(eq("appliedVoucher"), isNull());
    }

    @Test
    void testCalculateTotal_ComValorZeroOuNegativo_DeveIgnorarDesconto() {
        cartController.addItem(new CartItem(50.0, 2));
        
        cartController.calculateTotal("PERCENTAGE", 0.0, model);
        cartController.calculateTotal("FIXED_AMOUNT", -10.0, model);
        
        // Testa que não aplica desconto com valores inválidos
        verify(model, atLeast(2)).addAttribute(eq("appliedVoucher"), isNull());
    }

    @Test
    void testClearCart_DeveLimparCarrinho() {
        cartController.addItem(new CartItem(10.0, 1));
        cartController.addItem(new CartItem(20.0, 1));
        
        String redirect = cartController.clearCart();
        
        assertEquals("redirect:/cart", redirect);
        cartController.showCart(model);
        verify(model).addAttribute(eq("items"), argThat(items -> 
            ((java.util.List<?>) items).isEmpty()
        ));
    }

    @Test
    void testRemoveItem_ComIndiceValido_DeveRemoverItem() {
        cartController.addItem(new CartItem(10.0, 1));
        cartController.addItem(new CartItem(20.0, 1));
        cartController.addItem(new CartItem(30.0, 1));
        
        String redirect = cartController.removeItem(1);
        
        assertEquals("redirect:/cart", redirect);
        cartController.showCart(model);
        verify(model).addAttribute(eq("items"), argThat(items -> 
            ((java.util.List<?>) items).size() == 2
        ));
    }

    @Test
    void testRemoveItem_ComIndiceInvalido_DeveManterItens() {
        cartController.addItem(new CartItem(10.0, 1));
        cartController.addItem(new CartItem(20.0, 1));
        
        // Tentar remover índice negativo
        cartController.removeItem(-1);
        // Tentar remover índice maior que o tamanho
        cartController.removeItem(10);
        
        cartController.showCart(model);
        verify(model).addAttribute(eq("items"), argThat(items -> 
            ((java.util.List<?>) items).size() == 2
        ));
    }

    @Test
    void testRemoveItem_ComIndiceZero_DeveRemoverPrimeiroItem() {
        CartItem item1 = new CartItem(10.0, 1);
        CartItem item2 = new CartItem(20.0, 1);
        cartController.addItem(item1);
        cartController.addItem(item2);
        
        cartController.removeItem(0);
        
        cartController.showCart(model);
        verify(model).addAttribute(eq("items"), argThat(items -> 
            ((java.util.List<?>) items).size() == 1
        ));
    }

    @Test
    void testCalculateTotal_ComVoucherTipoDesconhecido_DeveIgnorarDesconto() {
        cartController.addItem(new CartItem(100.0, 1));
        
        String viewName = cartController.calculateTotal("UNKNOWN_TYPE", 10.0, model);
        
        assertEquals("cart", viewName);
        // Deve calcular sem desconto pois o tipo não é reconhecido
        verify(model).addAttribute(eq("total"), eq(100.0));
    }

    @Test
    void testShowCart_CarrinhoVazio_TotalDeveSerZero() {
        String viewName = cartController.showCart(model);
        
        assertEquals("cart", viewName);
        verify(model).addAttribute(eq("total"), eq(0.0));
    }

    @Test
    void testCalculateTotal_DescontoMaiorQueSubtotal_TotalDeveSerZero() {
        cartController.addItem(new CartItem(10.0, 1)); // Subtotal: 10.0
        
        String viewName = cartController.calculateTotal("FIXED_AMOUNT", 100.0, model);
        
        assertEquals("cart", viewName);
        verify(model).addAttribute(eq("total"), eq(0.0));
    }
}

