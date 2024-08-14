//package com.neo.neomarket.controller;
//
//import com.neo.neomarket.dto.ExchangeNeoPayDTO;
//import com.neo.neomarket.dto.UserDTO;
//import com.neo.neomarket.dto.WishDTO;
////import com.neo.neomarket.service.UserService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Tag(name = "User API", description = "User Controller")
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/api")
//public class UserController {
//
//    private final UserService userService;
//
//    @Operation(summary = "위시 리스트 조회", description = "파라미터로 받은 유저 id에 따라서 유저의 위시리스트 반환")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공적으로 위시 리스트를 조회했습니다.",
//                    content = @Content(mediaType = "application/json",
//                    array = @ArraySchema(schema = @Schema(implementation = WishDTO.class)))),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
//            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.", content = @Content),
//            @ApiResponse(responseCode = "422", description = "잘못된 위시리스트 데이터가 있습니다.", content = @Content),
//            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
//    })
//    @GetMapping("/users/{id}/wish")
//    public ResponseEntity<List<WishDTO>> getWishes(@PathVariable(name = "id") Long id){
//        List<WishDTO> wishes = userService.findWishAll(id);
//
//        return ResponseEntity.ok().body(wishes);
//    }
//
//
//    @Operation(summary = "유저 정보 조회", description = "파라미터로 받은 유저 id에 따라서 유저 정보 반환")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공적으로 유저 정보를 조회했습니다.",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = UserDTO.class))),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
//            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.", content = @Content),
//            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
//    })
//    @GetMapping("/users/{id}/info")
//    public ResponseEntity<UserDTO> userInfo(@PathVariable(name = "id") Long id){
//        UserDTO userDTO = userService.userInfo(id);
//
//        return ResponseEntity.ok().body(userDTO);
//    }
//
//
//    @Operation(summary = "네오 페이 충전", description = "네오 페이를 충전하는 기능")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "네오 페이를 성공적으로 충전했습니다."),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
//            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
//            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
//    })
//    @PostMapping("/users/charge")
//    public ResponseEntity<Void> chargeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO){
//        userService.chargeNeoPay(exchangeNeoPayDTO);
//
//        return ResponseEntity.ok().build();
//    }
//
//
//    @Operation(summary = "네오 페이 환전", description = "네오 페이를 환전하는 기능")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "네오 페이를 성공적으로 환전했습니다."),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
//            @ApiResponse(responseCode = "402", description = "잔액이 부족합니다."),
//            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
//            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
//    })
//    @PostMapping("/users/exchange")
//    public ResponseEntity<Void> exchangeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO){
//        userService.exchangeNeoPay(exchangeNeoPayDTO);
//
//        return ResponseEntity.ok().build();
//    }
//}
