package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.constants.ApiEndpointConstant;
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

    @GetMapping(value = ApiEndpointConstant.PING)
    public ResponseEntity<String> homePage() {
        log.info("Requesting ping");
        growAPIManager.getAllStockDetails();
        return new ResponseEntity<>("Hello stockers, I am up and running!!", HttpStatus.OK);
    }
}
