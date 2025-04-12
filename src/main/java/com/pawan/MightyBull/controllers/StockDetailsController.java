package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.services.stock.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.StockDetails.BASE)
public class StockDetailsController {

    private final StockService stockService;

    @Autowired
    public StockDetailsController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = ApiEndpointConstant.StockDetails.WIDGETS)
    public SuccessResponse<?> getStockWidgets(@RequestParam(value = "score_range", required = false) List<String> scoreRange,
                                              @RequestParam(value = "sort_by", required = false) List<String> sortBy,
                                              @RequestParam(value = "page_number", defaultValue = "0") Integer pageNumber,
                                              @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        log.info("STOCK_DETAILS_CONTROLLER::getStockWidgets Request received foe getting stock widgets for: {}, {}, {}, {}", scoreRange, sortBy, pageNumber, pageSize);
        return new SuccessResponse<>(stockService.getStockWidgets(scoreRange, sortBy, pageNumber, pageSize));
    }

    @GetMapping(value = ApiEndpointConstant.StockDetails.WIDGET_DETAILS)
    public SuccessResponse<?> getStockWidgetDetails(@PathVariable(value = "stock_id") String stockId) {
        log.info("STOCK_DETAILS_CONTROLLER::getStockWidgetDetails Request received for getting stock widget details for: {}", stockId);
        return new SuccessResponse<>(stockService.getStockWidgetDetails(stockId));
    }

    @GetMapping(value = ApiEndpointConstant.StockDetails.SEARCH)
    public SuccessResponse<?> searchStocks(@RequestParam(value = "name") String StockName) {
        log.info("STOCK_DETAILS_CONTROLLER::searchStocks Request received for getting stock search for: {}", StockName);
        return new SuccessResponse<>(stockService.searchStocks(StockName));
    }
}
