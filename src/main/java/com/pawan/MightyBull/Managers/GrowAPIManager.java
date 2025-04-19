package com.pawan.MightyBull.Managers;

import com.pawan.MightyBull.WebClients.GrowWebClient;
import com.pawan.MightyBull.dto.grow.GrowStocks;
import com.pawan.MightyBull.dto.grow.request.GrowIndexDetails;
import com.pawan.MightyBull.dto.grow.request.GrowIndexRequest;
import com.pawan.MightyBull.dto.grow.request.GrowStockRequest;
import com.pawan.MightyBull.dto.grow.response.GrowIndexResponse;
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

    public GrowStocks getAllStockDetails(int pageNumber, int pageSize) {
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
        GrowStockRequest request = new GrowStockRequest(listFilters, objFilters, pageNumber, pageSize, "NA", "ASC");
        return growWebClient.getAllStockDetails(request);
    }

    public GrowIndexResponse getIndexDetails() {
        Map<String, Map<String, List<String>>> exchangeAggReqMap = new HashMap<>();
        Map<String, List<String>> nse = new HashMap<>();
        nse.put("priceSymbolList", new ArrayList<>());
        nse.put("indexSymbolList", List.of("NIFTY",
                "BANKNIFTY",
                "FINNIFTY",
                "NIFTYMIDSELECT",
                "INDIAVIX",
                "NIFTYTOTALMCAP",
                "NIFTYJR",
                "NIFTY100",
                "NIFTYMIDCAP",
                "NIFTY500",
                "NIFTYAUTO",
                "NIFTYSMALL",
                "NIFTYFMCG",
                "NIFTYMETAL",
                "NIFTYPHARMA",
                "NIFTYPSUBANK",
                "NIFTYIT",
                "NIFTYSMALLCAP250",
                "NIFTYMIDCAP150",
                "NIFTYCDTY"));

        Map<String, List<String>> bse = new HashMap<>();
        bse.put("priceSymbolList", new ArrayList<>());
        bse.put("indexSymbolList", List.of("1",
                "14",
                "2",
                "19",
                "23"));
        exchangeAggReqMap.put("NSE", nse);
        exchangeAggReqMap.put("BSE", bse);
        GrowIndexRequest request = new GrowIndexRequest(exchangeAggReqMap);
        GrowIndexResponse response = growWebClient.getIndianIndexDetails(request);
        return response;
    }

    public GrowIndexResponse getGlobalIndexDetails() {
        return growWebClient.getGlobalIndexDetails();
    }

    public GrowIndexDetails getIndexDetails(String indexId) {
        return growWebClient.getIndexDetails(indexId);
    }


}
