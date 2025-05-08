package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.services.reporting.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pawan Saini
 * Created on 05/11/24.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Reporting.BASE)
public class AnalysisController {

    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping(value = ApiEndpointConstant.Reporting.STOCK_ANALYSIS)
    public SuccessResponse<?> stockAnalysis() {
        log.info("REPORTING_CONTROLLER ::: Received request for getting stock analysis.");
        return new SuccessResponse<>( analysisService.stockAnalysis());
    }

    @GetMapping(value = ApiEndpointConstant.Reporting.HIGH_DIVIDEND)
    public SuccessResponse<?> highDividendStocks(@RequestParam(value = "page_number", defaultValue = "0") Integer pageNumber,
                                                 @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        log.info("REPORTING_CONTROLLER ::: Received request for getting high dividend stocks page: {}, {}.", pageNumber, pageSize);
        return new SuccessResponse<>( analysisService.highDividendStocks(pageNumber, pageSize));
    }
}
