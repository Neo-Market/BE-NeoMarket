package com.neo.neomarket.controller;

import com.neo.neomarket.dto.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.UserDTO;
import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}/wish")
    public ResponseEntity<List<WishDTO>> getWishes(@PathVariable(name = "id") Long id){
        List<WishDTO> wishes = userService.findWishAll(id);

        return ResponseEntity.ok().body(wishes);
    }

    @GetMapping("/users/{id}/info")
    public ResponseEntity<UserDTO> userInfo(@PathVariable(name = "id") Long id){
        UserDTO userDTO = userService.userInfo(id);

        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/users/charge")
    public ResponseEntity<Void> chargeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO){
        userService.chargeNeoPay(exchangeNeoPayDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/exchange")
    public ResponseEntity<Void> exchangeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO){
        userService.exchangeNeoPay(exchangeNeoPayDTO);

        return ResponseEntity.ok().build();
    }
}
