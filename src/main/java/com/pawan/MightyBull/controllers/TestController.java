package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.dto.BaseDto;
import com.pawan.MightyBull.dto.Screener.ScreenerStockDetails;
import com.pawan.MightyBull.dto.communication.FundamentalStockEmailDto;
import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.mapper.ScreenerStockDetailsMapper;
import com.pawan.MightyBull.services.communication.FundamentalStockEmailService;
import com.pawan.MightyBull.services.grow.StockDetailsService;
import com.pawan.MightyBull.services.grow.StockPriceService;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private FundamentalStockEmailService fundamentalStockEmailService;

    @Autowired
    private ScreenerStockDetailsDao stockDetailsDao;

    @GetMapping(value = ApiEndpointConstant.PING)
    public ResponseEntity<String> homePage() {
        log.info("Requesting ping");
//        ScreenerStockDetails stockDetails1 = ScreenerStockDetailsMapper.INSTANCE.mapEntityToDto(stockDetailsDao.getByStockId("KALYANKJIL").orElse(null));
//        ScreenerStockDetails stockDetails2 = ScreenerStockDetailsMapper.INSTANCE.mapEntityToDto(stockDetailsDao.getByStockId("TATAMOTORS").orElse(null));
//        ScreenerStockDetails stockDetails3 = ScreenerStockDetailsMapper.INSTANCE.mapEntityToDto(stockDetailsDao.getByStockId("GRAVITA").orElse(null));
//        ScreenerStockDetails stockDetails4 = ScreenerStockDetailsMapper.INSTANCE.mapEntityToDto(stockDetailsDao.getByStockId("IRFC").orElse(null));
//        ScreenerStockDetails stockDetails5 = ScreenerStockDetailsMapper.INSTANCE.mapEntityToDto(stockDetailsDao.getByStockId("VEDL").orElse(null));
//        FundamentalStockEmailDto emailDto = new FundamentalStockEmailDto(List.of(stockDetails1, stockDetails2, stockDetails3, stockDetails4, stockDetails5));
//        fundamentalStockEmailService.send(emailDto);
        return new ResponseEntity<>("Hello stockers, I am up and running!!", HttpStatus.OK);
    }
}
