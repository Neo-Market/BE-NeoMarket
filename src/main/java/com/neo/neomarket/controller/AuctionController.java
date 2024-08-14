package com.neo.neomarket.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auction API", description = "Auction Controller")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {
}
