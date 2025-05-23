package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.Screener.ScreenerStockDetails;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.services.ScoringService;
import com.pawan.MightyBull.services.screener.ScreenerService;
import com.pawan.MightyBull.utils.GsonUtils;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final ScoringService scoringService;

    @Autowired
    public ScreenerController(ScreenerService screenerService,
                              ScoringService scoringService) {
        this.screenerService = screenerService;
        this.scoringService = scoringService;
    }

    @PostMapping(value = ApiEndpointConstant.Screener.STOCK_DETAILS)
    public SuccessResponse<?> addStockDetails(@RequestBody ScreenerStockDetails stockDetails) {
        log.info("SCREENER_CONTROLLER ::: Received request for adding stock details for stockData: {}", GsonUtils.getGson().toJson(stockDetails));
        String stockId = StockUtils.getStockId(stockDetails.getBseCode(), stockDetails.getNseCode());
        stockDetails.setStockId(stockId);
        screenerService.addStockDetails(stockId, stockDetails);
        scoringService.generateStockScore(stockId);
        return new SuccessResponse<>("SUCCESS");
    }
}
