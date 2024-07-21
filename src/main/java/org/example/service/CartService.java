package org.example.service;
import org.example.dto.CartItemDTO;




    public interface CartService {
        public void addItemToCard(CartItemDTO cartItem);

        void addItemToCard();

        public void removeItemFromCard(Integer itemCode, String username);
    }

