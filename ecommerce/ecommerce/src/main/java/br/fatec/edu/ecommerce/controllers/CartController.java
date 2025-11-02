package br.fatec.edu.ecommerce.controllers;

import br.fatec.edu.ecommerce.model.CartItem;
import br.fatec.edu.ecommerce.model.Voucher;
import br.fatec.edu.ecommerce.service.CartCalculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartCalculator cartCalculator;
    private final List<CartItem> cartItems;

    public CartController() {
        this.cartCalculator = new CartCalculator();
        this.cartItems = new ArrayList<>();
    }

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("items", cartItems);
        model.addAttribute("cartItem", new CartItem());
        model.addAttribute("voucher", new Voucher());
        model.addAttribute("total", cartCalculator.calculateTotal(cartItems, null));
        return "cart";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute CartItem cartItem) {
        cartItems.add(cartItem);
        return "redirect:/cart";
    }

    @PostMapping("/calculate")
    public String calculateTotal(@RequestParam(value = "type", required = false) String voucherType,
                                 @RequestParam(value = "value", required = false) Double voucherValue,
                                 Model model) {
        model.addAttribute("items", cartItems);
        model.addAttribute("cartItem", new CartItem());
        model.addAttribute("voucher", new Voucher());
        
        // Criar voucher se os dados foram fornecidos
        Voucher voucherToUse = null;
        if (voucherType != null && !voucherType.isEmpty() && voucherValue != null && voucherValue > 0) {
            Voucher voucher = new Voucher();
            if ("PERCENTAGE".equals(voucherType)) {
                voucher.setType(Voucher.Type.PERCENTAGE);
            } else if ("FIXED_AMOUNT".equals(voucherType)) {
                voucher.setType(Voucher.Type.FIXED_AMOUNT);
            }
            voucher.setValue(voucherValue);
            voucherToUse = voucher;
        }
        
        Double total = cartCalculator.calculateTotal(cartItems, voucherToUse);
        model.addAttribute("total", total);
        model.addAttribute("appliedVoucher", voucherToUse);
        
        return "cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartItems.clear();
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam int index) {
        if (index >= 0 && index < cartItems.size()) {
            cartItems.remove(index);
        }
        return "redirect:/cart";
    }
}

