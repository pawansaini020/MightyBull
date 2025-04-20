package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.enums.IndexType;
import com.pawan.MightyBull.services.mutualfund.MutualFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.MutualFund.BASE)
public class MutualFundController {

    private final MutualFundService mutualFundService;

    @Autowired
    public MutualFundController(MutualFundService mutualFundService) {
        this.mutualFundService = mutualFundService;
    }

    @GetMapping(value = ApiEndpointConstant.MutualFund.WIDGETS)
    public SuccessResponse<?> getMutualFundWidgets(@RequestParam(value = "page_number", defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        log.info("MUTUAL_FUND_CONTROLLER::getMutualFundWidgets Request received for fetching mutual fund widgets: {}, {}", pageNumber, pageSize);
        return mutualFundService.getMutualFundWidgets(pageNumber, pageSize);
    }

//    @GetMapping(value = ApiEndpointConstant.MutualFund.WIDGET_DETAILS)
//    public SuccessResponse<?> getIndexWidgetDetails(@PathVariable(value = "index_id") String indexId) {
//        log.info("STOCK_DETAILS_CONTROLLER::getIndexWidgetDetails Request received for getting index widget details: {}", indexId);
//        return new SuccessResponse<>(indexService.getIndexWidgetDetails(indexId));
//    }
}
