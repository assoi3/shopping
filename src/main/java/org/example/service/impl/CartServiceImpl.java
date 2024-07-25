package org.example.service.impl;
import org.example.dto.CartItemDTO;
import org.example.dto.ItemDTO;
import org.example.entities.CartItem;
import org.example.repositories.CartItemRepository;
import org.example.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{





        private final CartItemRepository cartItemRepository;
        private final RestTemplate restTemplate;

        public CartServiceImpl(CartItemRepository cartItemRepository, RestTemplate restTemplate) {
            this.cartItemRepository = cartItemRepository;
            this.restTemplate = restTemplate;
        }


        @Override
        public void addItemToCard(CartItemDTO cartItem) {
            Optional<CartItem> optCartItem = cartItemRepository.findCartItemByItemCodeAndUsername(cartItem.getItemCode(), cartItem.getUsername());

            if (optCartItem.isEmpty()){
                assert itemInStock != null;
                if (itemInStock.getQty() < cartItem.getQty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock!");
                cartItemRepository.save(new CartItem(cartItem.getUsername(), cartItem.getItemCode(), cartItem.getQty()));
            }else{
                CartItem cartItemEntity = optCartItem.get();
                assert itemInStock != null;
                if (itemInStock.getQty() < (cartItem.getQty() + cartItemEntity.getQty()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock!");
                cartItemEntity.setQty(cartItemEntity.getQty() + cartItem.getQty());
                cartItemRepository.save(cartItemEntity);
            }

        }

    @Override
    public void addItemToCard() {

    }

    @Override
        public void removeItemFromCard(Integer itemCode, String username) {
            CartItem cartItem = cartItemRepository.findCartItemByItemCodeAndUsername(itemCode, username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item doesn't exist"));
            cartItemRepository.delete(cartItem);
        }
    }

