package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.grow.response.SuccessResponse;
import com.pawan.MightyBull.services.grow.StockDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Grow.BASE)
public class GrowController {

    private final StockDetailsService stockDetailsService;

    @Autowired
    public GrowController(StockDetailsService stockDetailsService) {
        this.stockDetailsService = stockDetailsService;
    }

    @GetMapping(value = ApiEndpointConstant.Grow.ALL_STOCKS)
    public SuccessResponse<?> addStockDetails() {
        log.info("GROW_CONTROLLER ::: Received request for getting stock details");
        return new SuccessResponse<>(stockDetailsService.getStockId());
    }
}
