package br.fatec.edu.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EcommerceApplicationTest {

    @Test
    void testMainMethod_DeveIniciarSemErros() {
        // Testa se o método main pode ser chamado sem lançar exceções
        assertDoesNotThrow(() -> {
            String[] args = {};
            EcommerceApplication.main(args);
        });
    }

    @Test
    void testContextLoads_DeveCarregarContextoSpring() {
        // Este teste garante que o Spring Boot consegue carregar o contexto
        // O @SpringBootTest já faz isso automaticamente
        assertDoesNotThrow(() -> {
            // Contexto é carregado automaticamente pelo SpringBootTest
        });
    }
}

