package com.neo.neomarket.controller;

import com.neo.neomarket.dto.wish.PostShowDTO;
import com.neo.neomarket.dto.user.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.user.UserInfoDTO;
import com.neo.neomarket.dto.user.UserSaveDTO;
import com.neo.neomarket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "User API", description = "User Controller")
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@AuthenticationPrincipal OAuth2User oAuth2User,
                                           @RequestBody UserSaveDTO userSaveDto,
                                           UriComponentsBuilder uriComponentsBuilder) {

        String email = oAuth2User.getAttribute("email");

        // OAuth2에서 가져온 정보와 사용자 입력 정보를 합쳐 사용자 생성
        URI location = uriComponentsBuilder.path("/users/{id}")
                .buildAndExpand(userService.createUser(oAuth2User, userSaveDto)).toUri();

        return ResponseEntity.created(location).build();
    }

    // 회원가입 페이지에서 사용자에게 OAuth2로 가져온 기본 정보를 보여주기 위해 사용
    @GetMapping("/session/info")
    public ResponseEntity<Map<String, String>> getSessionInfo(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, String> userInfo = Map.of(
                "name", oAuth2User.getAttribute("name"),
                "email", oAuth2User.getAttribute("email"),
                "picture", oAuth2User.getAttribute("picture")
        );
        return ResponseEntity.ok(userInfo);
    }

    // 로그인된 사용자의 정보를 제공
    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 사용자 인증되지 않은 경우
        }

        String email = oAuth2User.getAttribute("email");  // OAuth2User에서 이메일 속성 추출
        UserInfoDTO userInfoDTO = userService.getUserInfoByEmail(email);  // DB에서 사용자 정보 조회

        if (userInfoDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 사용자 정보를 찾을 수 없는 경우
        }

        return ResponseEntity.ok(userInfoDTO);
    }


    @Operation(summary = "네오 페이 충전", description = "네오 페이를 충전하는 기능")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "네오 페이를 성공적으로 충전했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")})
    @PostMapping("/charge")
    public ResponseEntity<Void> chargeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO) {
        userService.chargeNeoPay(exchangeNeoPayDTO);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "네오 페이 환전", description = "네오 페이를 환전하는 기능")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "네오 페이를 성공적으로 환전했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "402", description = "잔액이 부족합니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")})
    @PostMapping("/exchange")
    public ResponseEntity<Void> exchangeNeoPay(@RequestBody ExchangeNeoPayDTO exchangeNeoPayDTO) {
        userService.exchangeNeoPay(exchangeNeoPayDTO);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "위시 리스트 조회", description = "파라미터로 받은 유저 id에 따라서 유저의 위시리스트 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 위시 리스트를 조회했습니다.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PostShowDTO.class)))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "422", description = "잘못된 위시리스트 데이터가 있습니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)})
    @GetMapping("/{id}/wish")
    public ResponseEntity<List<PostShowDTO>> getWishes(@PathVariable(name = "id") Long id) {
        List<PostShowDTO> wishes = userService.findAllWish(id);
        return ResponseEntity.ok().body(wishes);
    }

}