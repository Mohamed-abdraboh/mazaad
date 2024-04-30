package com.global.mazaad.itemsOffer.endpoint;

import com.global.mazaad.itemsOffer.service.ItemsOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemsOfferController {
    private final ItemsOfferService itemsOfferService;
}
