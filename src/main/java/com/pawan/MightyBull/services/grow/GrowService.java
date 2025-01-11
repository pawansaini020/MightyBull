package com.pawan.MightyBull.services.grow;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.dto.grow.GrowStocks;
import com.pawan.MightyBull.utils.GsonUtils;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 06/11/24.
 */
@Slf4j
@Service
public class GrowService {

    private final GrowAPIManager growAPIManager;
    private final StockDetailsService stockDetailsService;
    private final StockPriceService stockPriceService;

    @Autowired
    public GrowService(GrowAPIManager growAPIManager,
                       StockDetailsService stockDetailsService,
                       StockPriceService stockPriceService) {
        this.growAPIManager = growAPIManager;
        this.stockDetailsService = stockDetailsService;
        this.stockPriceService = stockPriceService;
    }

    public void syncStockDetails(int startPage, int endPage) {
        for (int i = startPage; i < endPage; i++) {
            try {
                GrowStocks growStocks = growAPIManager.getAllStockDetails(i, 150);
                if (CollectionUtils.isNotEmpty(growStocks.getRecords())) {
                    for (GrowStockDetails growStockDetails : growStocks.getRecords()) {
                        try {
                            String stockId = StockUtils.getStockId(growStockDetails.getBseScriptCode(), growStockDetails.getNseScriptCode());
                            growStockDetails.setStockId(stockId);
                            stockDetailsService.persistGrowStockDetails(growStockDetails);
                            stockPriceService.persistGrowStockPriceDetails(growStockDetails);
//                            Thread.sleep(500);
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

    public List<String> getAllStockIds() {
        return stockDetailsService.getAllStockIds();
    }
}
