package ed.lab.ed1labo04.Service;

import ed.lab.ed1labo04.Entity.CartEntity;
import ed.lab.ed1labo04.Entity.CartItemEntity;
import ed.lab.ed1labo04.Entity.ProductEntity;
import ed.lab.ed1labo04.Model.CreateCartItemRequest;
import ed.lab.ed1labo04.Model.CreateCartRequest;
import ed.lab.ed1labo04.Repository.CartRepository;
import ed.lab.ed1labo04.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartEntity createCart(CreateCartRequest createCartRequest) {

        if (createCartRequest.getCartItems() == null || createCartRequest.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart items are required");
        }

        Map<Integer, ProductEntity> products = new HashMap<>();
        Map<Integer, Integer> requestedQuantities = new HashMap<>();

        for (CreateCartItemRequest itemRequest : createCartRequest.getCartItems()) {

            if (itemRequest.getProductId() == null) {
                throw new IllegalArgumentException("Product id is required");
            }

            if (itemRequest.getQuantity() == null || itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }

            ProductEntity productEntity = productRepository
                    .findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            products.put(itemRequest.getProductId(), productEntity);

            int currentQuantity = requestedQuantities.getOrDefault(itemRequest.getProductId(), 0);

            requestedQuantities.put(
                    itemRequest.getProductId(),
                    currentQuantity + itemRequest.getQuantity()
            );
        }

        for (Map.Entry<Integer, Integer> entry : requestedQuantities.entrySet()) {

            ProductEntity productEntity = products.get(entry.getKey());
            int quantityRequested = entry.getValue();

            if (productEntity.getQuantity() < quantityRequested) {
                throw new IllegalArgumentException("Not enough inventory");
            }
        }

        List<CartItemEntity> cartItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (CreateCartItemRequest itemRequest : createCartRequest.getCartItems()) {

            ProductEntity productEntity = products.get(itemRequest.getProductId());

            CartItemEntity cartItemEntity = new CartItemEntity();

            cartItemEntity.setProductId(itemRequest.getProductId());
            cartItemEntity.setName(productEntity.getName());
            cartItemEntity.setPrice(productEntity.getPrice());
            cartItemEntity.setQuantity(itemRequest.getQuantity());

            cartItems.add(cartItemEntity);

            totalPrice += productEntity.getPrice() * itemRequest.getQuantity();
        }

        for (Map.Entry<Integer, Integer> entry : requestedQuantities.entrySet()) {

            ProductEntity productEntity = products.get(entry.getKey());
            int quantityRequested = entry.getValue();

            productEntity.setQuantity(productEntity.getQuantity() - quantityRequested);

            productRepository.save(productEntity);
        }

        CartEntity cartEntity = new CartEntity();

        cartEntity.setCartItems(cartItems);
        cartEntity.setTotalPrice(totalPrice);

        return cartRepository.save(cartEntity);
    }

    public CartEntity getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    }






}
