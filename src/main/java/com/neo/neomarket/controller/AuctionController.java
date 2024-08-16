package com.neo.neomarket.controller;

import com.neo.neomarket.dto.BidRequestDTO;
import com.neo.neomarket.service.AuctionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auction API", description = "Auction Controller")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {
    private final AuctionService auctionService;

    @Operation(summary = "입찰 기능", description = "경매에 입찰을 하고 ES에 로그 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "입찰에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @PostMapping("/auction/bid")
    ResponseEntity<Void> bidAuction(@RequestBody BidRequestDTO bidRequestDTO){
        auctionService.bidAction(bidRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "낙찰 기능", description = "ES에서 데이터를 가져와 낙찰 처리를 하고 참여한 사용자에게 금액 환불")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "낙찰에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @PostMapping("/auction/bid/success")
    ResponseEntity<Void> bidAuctionSuccess(@RequestParam(name = "id")Long postId){
        auctionService.bidSuccessAction(postId);
        return ResponseEntity.ok().build();
    }

}
