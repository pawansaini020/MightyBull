package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.grow.Screener.ScreenerStockDetails;
import com.pawan.MightyBull.dto.grow.response.SuccessResponse;
import com.pawan.MightyBull.services.screener.ScreenerService;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Screener.BASE)
public class ScreenerController {

    private final ScreenerService screenerService;

    @Autowired
    public ScreenerController(ScreenerService screenerService) {
        this.screenerService = screenerService;
    }

    @PostMapping(value = ApiEndpointConstant.Screener.STOCK_DETAILS)
    public SuccessResponse<?> addStockDetails(@RequestParam(value = "stock_id") String stockId,
                                         @RequestBody ScreenerStockDetails stockDetails) {
        log.info("SCREENER_CONTROLLER ::: Received request for adding stock details for stockId: {}, stockData: {}", stockId, GsonUtils.getGson().toJson(stockDetails));
        screenerService.addStockDetails(stockId, stockDetails);
        return new SuccessResponse<>("SUCCESS");
    }
}
