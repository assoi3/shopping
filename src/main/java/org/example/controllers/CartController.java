package org.example.controllers;


import org.example.dto.CartItemDTO;
import org.example.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@CrossOrigin
@RequestMapping("/shopping-cart/main")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    private String getPrincipal(String authorizationToken){
        String token = authorizationToken.replaceAll("Basic ", "");
        String loginCredentials = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        return loginCredentials.split(":")[0];
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void addNewItemToCard(@RequestBody CartItemDTO cartItem, @RequestHeader String authorization){
        cartItem.setUsername(getPrincipal(authorization));
        this.cartService.addItemToCard(cartItem);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code:\\d+}")
    public void removeItemFromCard(@PathVariable Integer code, @RequestHeader String authorization){
        this.cartService.removeItemFromCard(code, getPrincipal(authorization));
    }
}