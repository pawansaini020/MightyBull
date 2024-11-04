package com.pawan.MightyBull.Managers;

import com.pawan.MightyBull.WebClients.GrowWebClient;
import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.dto.grow.GrowStocks;
import com.pawan.MightyBull.dto.grow.request.GrowStockRequest;
import com.pawan.MightyBull.services.grow.StockDetailsService;
import com.pawan.MightyBull.services.grow.StockPriceService;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Component
@Slf4j
public class GrowAPIManager {

    private final GrowWebClient growWebClient;
    private final StockDetailsService stockDetailsService;
    private final StockPriceService stockPriceService;

    @Autowired
    private GrowAPIManager(GrowWebClient growWebClient,
                           StockDetailsService stockDetailsService,
                           StockPriceService stockPriceService) {
        this.growWebClient = growWebClient;
        this.stockDetailsService = stockDetailsService;
        this.stockPriceService = stockPriceService;
    }

    public void getAllStockDetails() {
        Map<String, List<String>> listFilters = new HashMap<>();
        listFilters.put("INDUSTRY", new ArrayList<>());
        listFilters.put("INDEX", new ArrayList<>());
        Map<String, Map<String, Long>> objFilters = new HashMap<>();
        Map<String, Long> openPriceRange = new HashMap<>();
        openPriceRange.put("max", 100000L);
        openPriceRange.put("min", 0L);
        objFilters.put("CLOSE_PRICE", openPriceRange);
        Map<String, Long> marketCabRange = new HashMap<>();
        marketCabRange.put("max", 3000000000000000L);
        marketCabRange.put("min", 0L);
        objFilters.put("MARKET_CAP", marketCabRange);
        for(int i=10; i<293; i++) {
            try {
                GrowStockRequest request = new GrowStockRequest(listFilters, objFilters, i, 15, "NA", "ASC");
                GrowStocks growStocks = growWebClient.getAllStockDetails(request);
                if (CollectionUtils.isNotEmpty(growStocks.getRecords())) {
                    for (GrowStockDetails growStockDetails : growStocks.getRecords()) {
                        try {
                            stockDetailsService.persistGrowStockDetails(growStockDetails);
                            stockPriceService.persistGrowStockPriceDetails(growStockDetails);
                        } catch (Exception e) {
                            log.error("Error occurred while persisting stock: {}", GsonUtils.getGson().toJson(growStockDetails), e);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error occurred while persisting stock for: {}", i, e);
            }
        }
    }
}
