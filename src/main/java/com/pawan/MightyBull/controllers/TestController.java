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
//            String request = "{\n" +
//                    "            \"isin\": \"INE002A01018\",\n" +
//                    "            \"growwContractId\": \"GSTK500325\",\n" +
//                    "            \"companyName\": \"Reliance Industries\",\n" +
//                    "            \"companyShortName\": \"Reliance Industries\",\n" +
//                    "            \"searchId\": \"reliance-industries-ltd\",\n" +
//                    "            \"industryCode\": 146,\n" +
//                    "            \"bseScriptCode\": 500325,\n" +
//                    "            \"nseScriptCode\": \"RELIANCE\",\n" +
//                    "            \"yearlyHighPrice\": 3024.9,\n" +
//                    "            \"yearlyLowPrice\": 2111.68,\n" +
//                    "            \"closePrice\": 2929.65,\n" +
//                    "            \"marketCap\": 1984309000000000,\n" +
//                    "            \"livePriceDto\": {\n" +
//                    "                \"type\": \"LIVE_PRICE\",\n" +
//                    "                \"symbol\": \"RELIANCE\",\n" +
//                    "                \"tsInMillis\": 1730469334,\n" +
//                    "                \"open\": 1333.05,\n" +
//                    "                \"high\": 1341.95,\n" +
//                    "                \"low\": 1333.0,\n" +
//                    "                \"close\": 1332.05,\n" +
//                    "                \"ltp\": 1338.65,\n" +
//                    "                \"dayChange\": 6.600000000000136,\n" +
//                    "                \"dayChangePerc\": 0.49547689651290394,\n" +
//                    "                \"lowPriceRange\": 1198.85,\n" +
//                    "                \"highPriceRange\": 1465.25,\n" +
//                    "                \"volume\": 2127335,\n" +
//                    "                \"totalBuyQty\": 2252.0,\n" +
//                    "                \"totalSellQty\": 0.0,\n" +
//                    "                \"oiDayChange\": 0.0,\n" +
//                    "                \"oiDayChangePerc\": 0.0,\n" +
//                    "                \"lastTradeQty\": 6,\n" +
//                    "                \"lastTradeTime\": 1730469299\n" +
//                    "            }\n" +
//                    "        }";
//            GrowStockDetails growStockDetails = GsonUtils.getGson().fromJson(request, GrowStockDetails.class);
//            stockDetailsService.persistGrowStockDetails(growStockDetails);
//            stockPriceService.persistGrowStockPriceDetails(growStockDetails);
            growAPIManager.getAllStockDetails();
        } catch (Exception e) {
            log.error("Error occurred while persisting price: ", e);
        }
        return new ResponseEntity<>("Hello stockers, I am up and running!!", HttpStatus.OK);
    }
}
