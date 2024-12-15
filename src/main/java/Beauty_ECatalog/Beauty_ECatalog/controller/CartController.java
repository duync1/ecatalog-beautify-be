package Beauty_ECatalog.Beauty_ECatalog.controller;

import java.util.List;

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

    @GetMapping("/Cart/GetCart")
    public ResponseEntity<Cart> getCartByUserId(@RequestParam("email") String email){
        return ResponseEntity.ok().body(this.cartService.getCartByUser(email));
    }

    @PostMapping("/Cart/DeleteProductFromCart")
    public ResponseEntity<Cart> handleDeleteProduct(@RequestParam("email") String email, @RequestParam("productId") List<Long> productId){
        Cart cart = this.cartService.deleteProduct(email, productId);
        return ResponseEntity.ok().body(cart);
    }
}
