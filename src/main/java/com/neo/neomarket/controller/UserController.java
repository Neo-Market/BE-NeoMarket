package com.neo.neomarket.controller;

import com.neo.neomarket.dto.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.UserSaveDTO;
import com.neo.neomarket.dto.UserInfoDTO;
import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.service.UserService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@AuthenticationPrincipal OAuth2User principal,
                                           @RequestBody UserSaveDTO userSaveDto,
                                           UriComponentsBuilder uriComponentsBuilder) {
        URI location = uriComponentsBuilder.path("/users/{id}")
                .buildAndExpand(userService.createUser(principal, userSaveDto))
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/users/{id}/info")
    public ResponseEntity<UserInfoDTO> userInfo(@AuthenticationPrincipal OAuth2User principal,
                                                @PathVariable Long id) {
        UserInfoDTO userInfoDTO = userService.userInfo(principal, id);

        return ResponseEntity.ok().body(userInfoDTO);
    }

    @GetMapping("/users/{id}/wish")
    public ResponseEntity<List<WishDTO>> getWishes(@PathVariable Long id) {
        List<WishDTO> wishes = userService.findWishAll(id);

        return ResponseEntity.ok().body(wishes);
    }

    @PostMapping("/users/charge")
    public ResponseEntity<Void> chargeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO) {
        userService.chargeNeoPay(exchangeNeoPayDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/exchange")
    public ResponseEntity<Void> exchangeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO) {
        userService.exchangeNeoPay(exchangeNeoPayDTO);

        return ResponseEntity.ok().build();
    }
}