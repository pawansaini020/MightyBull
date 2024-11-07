package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.services.grow.StockDetailsService;
import com.pawan.MightyBull.services.grow.StockPriceService;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private GrowAPIManager growAPIManager;

    @Autowired
    private StockDetailsService stockDetailsService;

    @Autowired
    private StockPriceService stockPriceService;

    @GetMapping(value = ApiEndpointConstant.PING)
    public ResponseEntity<String> homePage() {
        try {
            log.info("Requesting ping");
        } catch (Exception e) {
            log.error("Error occurred while persisting price: ", e);
        }
        return new ResponseEntity<>("Hello stockers, I am up and running!!", HttpStatus.OK);
    }
}
