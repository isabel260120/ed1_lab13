package ed.lab.ed1labo04.Model;

import ed.lab.ed1labo04.Entity.CartEntity;

import java.util.List;

public class CreateCartRequest {
    private List<CreateCartItemRequest> cartItems;
    public List<CreateCartItemRequest> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CreateCartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }
}
