package Beauty_ECatalog.Beauty_ECatalog.service;

import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Cart;
import Beauty_ECatalog.Beauty_ECatalog.domain.CartDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.repository.CartDetailRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;
    private final CartDetailRepository cartDetailRepository;
    public CartService(CartRepository cartRepository, UserService userService, ProductService productService, CartDetailRepository cartDetailRepository){
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
        this.cartDetailRepository = cartDetailRepository;
    }

    public Cart handleAddProductToCart(String email, long productId, long quantity){
        User user = this.userService.handleGetUserByUsername(email);
        if(user != null){
            Cart cart = this.cartRepository.findByUser(user);
            if(cart == null){
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }
            Product product = this.productService.getProductById(productId);
            CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
            if(oldDetail == null){
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setPrice(product.getUnitPrice());
                cartDetail.setQuantity(quantity);
                this.cartDetailRepository.save(cartDetail);

                int s = cart.getSum() + 1;
                cart.setSum(s);
                this.cartRepository.save(cart);
            }
            else{
                oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                this.cartDetailRepository.save(oldDetail);
            }
            return cart;
        }
        return null;
    }
}
