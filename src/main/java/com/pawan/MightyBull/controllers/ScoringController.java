package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.dto.score.StockScoreInfoDTO;
import com.pawan.MightyBull.services.ScoringService;
import com.pawan.MightyBull.services.grow.GrowService;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 07/11/24.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Scoring.BASE)
public class ScoringController {

    private final ScoringService scoringService;
    private final GrowService growService;

    @Autowired
    public ScoringController(ScoringService scoringService,
                             GrowService growService) {
        this.scoringService = scoringService;
        this.growService = growService;
    }

    @PostMapping(value = ApiEndpointConstant.Scoring.CALCULATE_SCORE)
    public SuccessResponse<?> generateStockScore(@RequestParam(value = "stock_id") String stockId) {
        log.info("SCORING_CONTROLLER ::: Received request for generating score for: {}", stockId);
        return new SuccessResponse<>(scoringService.generateStockScore(stockId));
    }

    @PostMapping(value = ApiEndpointConstant.Scoring.SYNC)
    public SuccessResponse<?> syncStockScore(@RequestParam(value = "stock_id") String stockId,
                                             @RequestBody StockScoreInfoDTO scoreInfoDTO) {
        log.info("SCORING_CONTROLLER ::: Received request for syncing score for: {}, {}", stockId, GsonUtils.getGson().toJson(scoreInfoDTO));
        scoringService.syncStockScore(scoreInfoDTO.getScoreInfo());
        return new SuccessResponse<>("Success");
    }

    @PostMapping(value = ApiEndpointConstant.Scoring.CALCULATE_STOCK_SCORE)
    public SuccessResponse<?> syncStocksScore() {
        log.info("SCORING_CONTROLLER ::: Received request for calculate stocks score.");
        growService.getAllStockIds().forEach(scoringService::generateStockScore);
        return new SuccessResponse<>("Success");
    }
}
