package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.Item;
import com.pehchevskip.eimtproject.model.OrderItem;
import com.pehchevskip.eimtproject.model.ShoppingCart;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderItemService orderItemService;

    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart addItem(String username, Long itemId, int quantity) {
        Optional<User> user = userService.findByUsername(username);
        Optional<Item> item = itemService.findById(itemId);
        if (!user.isPresent() || !item.isPresent()) {
            return new ShoppingCart();
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item.get());
        orderItem.setQuantity(quantity);
        orderItemService.save(orderItem);

        ShoppingCart shoppingCart = user.get().getShoppingCart();
        Optional<OrderItem> optionalOrderItem = shoppingCart.getOrderItems().stream()
                .filter(it -> it.getItem().getId().equals(itemId))
                .findFirst();
        if (!optionalOrderItem.isPresent()) {
            shoppingCart.getOrderItems().add(orderItem);
        } else {
            if (quantity + optionalOrderItem.get().getQuantity() > 0) {
                optionalOrderItem.get().setQuantity(quantity + optionalOrderItem.get().getQuantity());
            } else if (quantity + optionalOrderItem.get().getQuantity() == 0) {
                shoppingCart.getOrderItems().remove(optionalOrderItem.get());
            }
        }
        userService.save(user.get());
        return shoppingCart;
    }

    public boolean removeItem(String username, Long itemId) {
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            return false;
        }

        List<OrderItem> cartItems = user.get().getShoppingCart().getOrderItems();
        if (cartItems.removeIf(currentItem -> currentItem.getItem().getId().equals(itemId))) {
            userService.save(user.get());
            return true;
        }
        return false;
    }

}
