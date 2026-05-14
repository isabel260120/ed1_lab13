package ed.lab.ed1labo04.Controller;
import ed.lab.ed1labo04.Entity.CartEntity;
import ed.lab.ed1labo04.Model.CreateCartRequest;
import ed.lab.ed1labo04.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartEntity> createCart(@RequestBody CreateCartRequest createCartRequest) {
        try {
            CartEntity cartEntity = cartService.createCart(createCartRequest);
            return ResponseEntity.status(201).body(cartEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartEntity> getCart(@PathVariable Long id) {
        try {
            CartEntity cartEntity = cartService.getCartById(id);
            return ResponseEntity.ok(cartEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
