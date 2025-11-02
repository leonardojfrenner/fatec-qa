package br.fatec.edu.ecommerce.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

    @Test
    void testIndex_DeveRetornarRedirectParaCart() {
        Index indexController = new Index();
        
        String viewName = indexController.index();
        
        assertEquals("redirect:/cart", viewName);
    }
}

