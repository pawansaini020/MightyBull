package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.services.constance.ConstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Constance.BASE)
public class ConstanceController {


    private ConstanceService constanceService;

    @Autowired
    private ConstanceController(ConstanceService constanceService) {
        this.constanceService = constanceService;
    }

    @GetMapping(value = ApiEndpointConstant.Constance.FETCH)
    public SuccessResponse<?> getConstanceValue(@RequestParam(value = "key") String key) {
        log.debug("CONSTANCE_CONTROLLER ::: Received request for getting constance for key: {}", key);
        return new SuccessResponse<>(constanceService.getConstanceValue(key));
    }

    @PostMapping(value = ApiEndpointConstant.Constance.RELOAD)
    public SuccessResponse<?> reloadConstance() {
        log.debug("CONSTANCE_CONTROLLER ::: Received request for reloading constance.");
        constanceService.reloadConstance();
        return new SuccessResponse();
    }

    @PostMapping(value = ApiEndpointConstant.Constance.UPDATE)
    public SuccessResponse<?> updateConstanceValue(@RequestParam(value = "key") String key,
                                                   @RequestBody Map<String, Object> value) {
        log.debug("CONSTANCE_CONTROLLER ::: Received request for updating constance for key: {}, {}", key, value);
        constanceService.updateConstanceValue(key, value);
        return new SuccessResponse<>();
    }
}
