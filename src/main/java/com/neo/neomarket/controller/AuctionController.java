package com.neo.neomarket.controller;

import com.neo.neomarket.dto.BidLogDTO;
import com.neo.neomarket.dto.UserExchangeLogDTO;
import com.neo.neomarket.service.impl.AuctionServiceImpl;
import com.neo.neomarket.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {
    private final AuctionServiceImpl auctionService;
    private final UserServiceImpl userService;


    @PostMapping(value = "/log/auction")
    public ResponseEntity<Void> recordBidLog(@RequestBody BidLogDTO bidLogDTO){
        auctionService.recordBidLog(bidLogDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/log/user")
    public ResponseEntity<Void> recordBidLog(@RequestBody UserExchangeLogDTO userExchangeLogDTO){
        userService.recordExchangeLog(userExchangeLogDTO);
        return ResponseEntity.ok().build();
    }


}
