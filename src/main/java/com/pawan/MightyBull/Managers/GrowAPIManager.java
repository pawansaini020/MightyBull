package com.pawan.MightyBull.Managers;

import com.pawan.MightyBull.WebClients.GrowWebClient;
import com.pawan.MightyBull.dto.grow.GrowStocks;
import com.pawan.MightyBull.dto.grow.request.GrowStockRequest;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private GrowAPIManager(GrowWebClient growWebClient) {
        this.growWebClient = growWebClient;
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
        GrowStockRequest request = new GrowStockRequest(listFilters, objFilters, 0, 15, "NA", "ASC");
        GrowStocks growStocks = growWebClient.getAllStockDetails(request);
        log.info("Grow stocks: {}", growStocks);
    }
}
