package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.services.scrapper.ScrapperService;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Scrapper.BASE)
public class ScrapperController {

    private final ScrapperService scrapperService;

    @Autowired
    public ScrapperController(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @PostMapping(value = ApiEndpointConstant.Scrapper.SCREENER_STOCK)
    public SuccessResponse<?> triggerScreenerStockScrapper(@RequestBody List<String> stockIds) {
        log.info("SCRAPPER_CONTROLLER ::: Received request for scrapping screener stocks: {}", stockIds);
        scrapperService.triggerScreenerStockScrapper(stockIds);
        return new SuccessResponse<>("SUCCESS");
    }
}
