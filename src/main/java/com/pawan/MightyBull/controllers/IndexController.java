package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.enums.IndexType;
import com.pawan.MightyBull.services.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Index.BASE)
public class IndexController {

    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping(value = ApiEndpointConstant.Index.WIDGETS)
    public SuccessResponse<?> getIndexWidgets(@RequestParam(value = "index_type") IndexType type) {
        log.info("STOCK_DETAILS_CONTROLLER::getIndexWidgets Request received for getting index widgets.");
        return new SuccessResponse<>(indexService.getIndexWidgets(type));
    }

    @GetMapping(value = ApiEndpointConstant.Index.WIDGET_DETAILS)
    public SuccessResponse<?> getIndexWidgetDetails(@PathVariable(value = "index_id") String indexId) {
        log.info("STOCK_DETAILS_CONTROLLER::getIndexWidgetDetails Request received for getting index widget details: {}", indexId);
        return new SuccessResponse<>(indexService.getIndexWidgetDetails(indexId));
    }
}
