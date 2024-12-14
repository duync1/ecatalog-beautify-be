package Beauty_ECatalog.Beauty_ECatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Beauty_ECatalog.Beauty_ECatalog.domain.Cart;
import Beauty_ECatalog.Beauty_ECatalog.service.CartService;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/Cart/AddProductToCart")
    public ResponseEntity<Cart> handleAddProductToCart(@RequestParam("email") String email, @RequestParam("productId")long productId, @RequestParam("quantity") long quantity){
        Cart cart = this.cartService.handleAddProductToCart(email, productId, quantity);
        return ResponseEntity.ok().body(cart);
    }

    
}
