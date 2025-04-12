package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.services.grow.GrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Grow.GROW_BASE)
public class GrowController {

    private final GrowService growService;

    @Autowired
    public GrowController(GrowService growService) {
        this.growService = growService;
    }

    @PostMapping(value = ApiEndpointConstant.Grow.SYNC_STOCKS)
    public SuccessResponse<?> syncStockDetails(@RequestParam(value = "start_page") Integer startPage,
                                               @RequestParam(value = "end_page") Integer endPage) {
        log.info("GROW_CONTROLLER ::: Received request for getting stock details for: {}, {}", startPage, endPage);
        growService.syncStockDetails(startPage, endPage);
        return new SuccessResponse<>("Success");
    }

    @GetMapping(value = ApiEndpointConstant.Grow.ALL_STOCKS)
    public SuccessResponse<?> getStockDetails() {
        log.info("GROW_CONTROLLER ::: Received request for getting stock details");
        return new SuccessResponse<>(growService.getAllStockIds());
    }

    @GetMapping(value = ApiEndpointConstant.Grow.INDEX_SYNC)
    public SuccessResponse<?> syncIndexDetails() {
        log.info("GROW_CONTROLLER ::: Received request for syncing index details");
        return new SuccessResponse<>(growService.syncIndexDetails());
    }
}
