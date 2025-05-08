package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.enums.IndexType;
import com.pawan.MightyBull.services.mutualfund.MutualFundDetailsService;
import com.pawan.MightyBull.services.mutualfund.MutualFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.MutualFund.BASE)
public class MutualFundController {

    private final MutualFundService mutualFundService;
    private final MutualFundDetailsService mutualFundDetailsService;

    @Autowired
    public MutualFundController(MutualFundService mutualFundService,
                                MutualFundDetailsService mutualFundDetailsService) {
        this.mutualFundService = mutualFundService;
        this.mutualFundDetailsService = mutualFundDetailsService;
    }

    @GetMapping(value = ApiEndpointConstant.MutualFund.WIDGETS)
    public SuccessResponse<?> getMutualFundWidgets(@RequestParam(value = "fund_house", required = false) String fundHouse,
                                                   @RequestParam(value = "category", required = false) String category,
                                                   @RequestParam(value = "cap", required = false) String cap,
                                                   @RequestParam(value = "page_number", defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        log.info("MUTUAL_FUND_CONTROLLER::getMutualFundWidgets Request received for fetching mutual fund widgets: {}, {}, {}, {}, {}", fundHouse, category, cap, pageNumber, pageSize);
        return mutualFundService.getMutualFundWidgets(fundHouse, category, cap, pageNumber, pageSize);
    }

    @GetMapping(value = ApiEndpointConstant.MutualFund.WIDGET_DETAILS)
    public SuccessResponse<?> getMutualFundWidgetDetails(@PathVariable(value = "mutual_fund_id") String mutualFundId) {
        log.info("STOCK_DETAILS_CONTROLLER::getMutualFundWidgetDetails Request received for getting mutual fund widget details: {}", mutualFundId);
        return new SuccessResponse<>(mutualFundDetailsService.getMutualFundWidgetDetails(mutualFundId));
    }
}
