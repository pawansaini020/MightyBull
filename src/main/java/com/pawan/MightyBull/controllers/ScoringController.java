package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.services.ScoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pawan Saini
 * Created on 07/11/24.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Scoring.BASE)
public class ScoringController {

    private final ScoringService scoringService;

    @Autowired
    public ScoringController(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @PostMapping(value = ApiEndpointConstant.Scoring.CALCULATE_SCORE)
    public SuccessResponse<?> generateStockScore(@RequestParam(value = "stock_id") String stockId) {
        log.info("SCORING_CONTROLLER ::: Received request for generating score for: {}", stockId);
        return new SuccessResponse<>(scoringService.generateStockScore(stockId));
    }
}
