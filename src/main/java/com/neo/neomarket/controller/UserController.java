package com.neo.neomarket.controller;

import com.neo.neomarket.dto.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.UserSaveDTO;
import com.neo.neomarket.dto.UserInfoDTO;
import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "User API", description = "User Controller")
@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@AuthenticationPrincipal OAuth2User principal,
                                           @RequestBody UserSaveDTO userSaveDto,
                                           UriComponentsBuilder uriComponentsBuilder) {
        if (principal == null) {
            logger.error("Principal is null, user is not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("Authenticated user: {}", principal.getName());
        logger.info("UserSaveDTO: {}", userSaveDto);

        URI location = uriComponentsBuilder.path("/users/{id}")
                .buildAndExpand(userService.createUser(principal, userSaveDto))
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }


    @GetMapping("/session-user/info")
    public ResponseEntity<Map<String, String>> getUserInfo(HttpSession session) {
        OAuth2User user = (OAuth2User) session.getAttribute("oauthUser");
        if (user != null) {
            Map<String, String> userInfo = Map.of(
                    "name", user.getAttribute("name"),
                    "email", user.getAttribute("email"),
                    "picture", user.getAttribute("picture")
            );
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @Operation(summary = "유저 정보 조회", description = "파라미터로 받은 유저 id에 따라서 유저 정보 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 유저 정보를 조회했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
    })
    @GetMapping("/users/{id}/info")
    public ResponseEntity<UserInfoDTO> userInfo(@AuthenticationPrincipal OAuth2User principal,
                                                @PathVariable Long id) {
        UserInfoDTO userInfoDTO = userService.getUserInfo(principal, id);

        return ResponseEntity.ok().body(userInfoDTO);
    }


    @Operation(summary = "네오 페이 충전", description = "네오 페이를 충전하는 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "네오 페이를 성공적으로 충전했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @PostMapping("/users/charge")
    public ResponseEntity<Void> chargeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO) {
        userService.chargeNeoPay(exchangeNeoPayDTO);

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "네오 페이 환전", description = "네오 페이를 환전하는 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "네오 페이를 성공적으로 환전했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "402", description = "잔액이 부족합니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @PostMapping("/users/exchange")
    public ResponseEntity<Void> exchangeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO) {
        userService.exchangeNeoPay(exchangeNeoPayDTO);

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "위시 리스트 조회", description = "파라미터로 받은 유저 id에 따라서 유저의 위시리스트 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 위시 리스트를 조회했습니다.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = WishDTO.class)))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "422", description = "잘못된 위시리스트 데이터가 있습니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
    })
    @GetMapping("/users/{id}/wish")
    public ResponseEntity<List<WishDTO>> getWishes(@PathVariable(name = "id") Long id) {
        List<WishDTO> wishes = userService.findWishAll(id);

        return ResponseEntity.ok().body(wishes);
    }

}