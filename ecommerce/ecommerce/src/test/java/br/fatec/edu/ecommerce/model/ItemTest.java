package br.fatec.edu.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testConstrutorPadrao_DeveCriarItemVazio() {
        Item item = new Item();
        
        assertNotNull(item);
        assertNull(item.getNome());
        assertNull(item.getDescricao());
        assertNull(item.getValor());
        assertNull(item.getQuantidade());
    }

    @Test
    void testConstrutorComParametros_DeveInicializarCorretamente() {
        Item item = new Item("Produto Teste", "Descrição do produto", 29.99, 10);
        
        assertNotNull(item);
        assertEquals("Produto Teste", item.getNome());
        assertEquals("Descrição do produto", item.getDescricao());
        assertEquals(29.99, item.getValor());
        assertEquals(10, item.getQuantidade());
    }

    @Test
    void testGetNome_DeveRetornarNomeCorreto() {
        Item item = new Item("Nome Teste", "Desc", 10.0, 1);
        
        assertEquals("Nome Teste", item.getNome());
    }

    @Test
    void testSetNome_DeveAtualizarNome() {
        Item item = new Item();
        
        item.setNome("Novo Nome");
        
        assertEquals("Novo Nome", item.getNome());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "Nome Qualquer", "Produto 123", "Item com espaço"})
    void testSetNome_ComDiferentesValores_DeveAtualizarCorretamente(String nome) {
        Item item = new Item();
        
        item.setNome(nome);
        
        assertEquals(nome, item.getNome());
    }

    @Test
    void testGetDescricao_DeveRetornarDescricaoCorreta() {
        Item item = new Item("Nome", "Descrição Teste", 10.0, 1);
        
        assertEquals("Descrição Teste", item.getDescricao());
    }

    @Test
    void testSetDescricao_DeveAtualizarDescricao() {
        Item item = new Item();
        
        item.setDescricao("Nova Descrição");
        
        assertEquals("Nova Descrição", item.getDescricao());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "Descrição qualquer", "Descrição com números 123", "Descrição longa com muitos caracteres"})
    void testSetDescricao_ComDiferentesValores_DeveAtualizarCorretamente(String descricao) {
        Item item = new Item();
        
        item.setDescricao(descricao);
        
        assertEquals(descricao, item.getDescricao());
    }

    @Test
    void testGetValor_DeveRetornarValorCorreto() {
        Item item = new Item("Nome", "Desc", 50.75, 1);
        
        assertEquals(50.75, item.getValor());
    }

    @Test
    void testSetValor_DeveAtualizarValor() {
        Item item = new Item();
        
        item.setValor(99.99);
        
        assertEquals(99.99, item.getValor());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {0.0, 0.01, 10.0, 100.0, 999.99, -10.0})
    void testSetValor_ComDiferentesValores_DeveAtualizarCorretamente(Double valor) {
        Item item = new Item();
        
        item.setValor(valor);
        
        assertEquals(valor, item.getValor());
    }

    @Test
    void testGetQuantidade_DeveRetornarQuantidadeCorreta() {
        Item item = new Item("Nome", "Desc", 10.0, 25);
        
        assertEquals(25, item.getQuantidade());
    }

    @Test
    void testSetQuantidade_DeveAtualizarQuantidade() {
        Item item = new Item();
        
        item.setQuantidade(5);
        
        assertEquals(5, item.getQuantidade());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {0, 1, 2, 10, 100, 1000, -5})
    void testSetQuantidade_ComDiferentesValores_DeveAtualizarCorretamente(Integer quantidade) {
        Item item = new Item();
        
        item.setQuantidade(quantidade);
        
        assertEquals(quantidade, item.getQuantidade());
    }

    @Test
    void testGettersESetters_Completamente() {
        Item item = new Item();
        
        item.setNome("Produto");
        item.setDescricao("Descrição");
        item.setValor(15.50);
        item.setQuantidade(3);
        
        assertEquals("Produto", item.getNome());
        assertEquals("Descrição", item.getDescricao());
        assertEquals(15.50, item.getValor());
        assertEquals(3, item.getQuantidade());
        
        // Atualizar todos novamente
        item.setNome("Produto Atualizado");
        item.setDescricao("Nova Descrição");
        item.setValor(20.0);
        item.setQuantidade(5);
        
        assertEquals("Produto Atualizado", item.getNome());
        assertEquals("Nova Descrição", item.getDescricao());
        assertEquals(20.0, item.getValor());
        assertEquals(5, item.getQuantidade());
    }

    @Test
    void testConstrutorEConstrutorPadrao_AmbosFuncionam() {
        Item item1 = new Item();
        Item item2 = new Item("Nome", "Desc", 10.0, 1);
        
        assertNotNull(item1);
        assertNotNull(item2);
        assertEquals("Nome", item2.getNome());
        assertEquals("Desc", item2.getDescricao());
        assertEquals(10.0, item2.getValor());
        assertEquals(1, item2.getQuantidade());
    }

    @Test
    void testItem_ComTodosOsCamposNulos_DeveFuncionar() {
        Item item = new Item(null, null, null, null);
        
        assertNull(item.getNome());
        assertNull(item.getDescricao());
        assertNull(item.getValor());
        assertNull(item.getQuantidade());
    }
}

