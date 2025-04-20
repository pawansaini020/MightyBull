package com.pawan.MightyBull.services.grow;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.dto.grow.GrowMutualFundDetails;
import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.dto.grow.GrowStocks;
import com.pawan.MightyBull.dto.grow.request.GrowIndexDetails;
import com.pawan.MightyBull.dto.grow.response.GrowIndexResponse;
import com.pawan.MightyBull.dto.grow.response.GrowMutualFundResponse;
import com.pawan.MightyBull.dto.grow.response.IndexDto;
import com.pawan.MightyBull.dto.index.IndexWidget;
import com.pawan.MightyBull.enums.IndexType;
import com.pawan.MightyBull.services.IndexService;
import com.pawan.MightyBull.services.mutualfund.MutualFundService;
import com.pawan.MightyBull.utils.GsonUtils;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final IndexService indexService;
    private final MutualFundService mutualFundService;

    @Autowired
    public GrowService(GrowAPIManager growAPIManager,
                       StockDetailsService stockDetailsService,
                       StockPriceService stockPriceService,
                       IndexService indexService,
                       MutualFundService mutualFundService) {
        this.growAPIManager = growAPIManager;
        this.stockDetailsService = stockDetailsService;
        this.stockPriceService = stockPriceService;
        this.indexService = indexService;
        this.mutualFundService = mutualFundService;
    }

    public void syncStockDetails(int startPage, int endPage) {
        for (int i = startPage; i <= endPage; i++) {
            try {
                GrowStocks growStocks = growAPIManager.getAllStockDetails(i, 15);
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

    public List<IndexWidget> syncIndexDetails() {
        GrowIndexResponse growIndexResponse = growAPIManager.getIndexDetails();
        growIndexResponse.getExchangeAggRespMap()
                .get("NSE")
                .get("indexLivePointsMap")
                .values()
                .forEach(dto -> {
                    dto.setType(IndexType.INDIAN.name());
                    dto.setName(dto.getSymbol());
                    indexService.syncIndex(dto);
                });
        growIndexResponse.getExchangeAggRespMap()
                .get("BSE")
                .get("indexLivePointsMap")
                .values()
                .forEach(dto -> {
                    dto.setType(IndexType.INDIAN.name());
                    dto.setName(dto.getSymbol());
                    indexService.syncIndex(dto);
                });
        GrowIndexResponse globalIndexDetails = growAPIManager.getGlobalIndexDetails();
        globalIndexDetails.getAggregatedGlobalInstrumentDto()
                .forEach(dto -> {
                    IndexDto index = dto.getInstrumentDetailDto();
                    IndexDto price = dto.getLivePriceDto();
                    price.setName(index.getName());
                    price.setSymbol(index.getSymbol());
                    price.setCountry(index.getCountry());
                    price.setType(IndexType.GLOBAL.name());
                    price.setLogoUrl(index.getLogoUrl());
                    indexService.syncIndex(price);
                });
        return indexService.getIndexWidgets(IndexType.INDIAN);
    }

    public void syncMutualFundDetails(Integer startPage, Integer endPage) {
        for (int i = startPage; i <= endPage; i++) {
            try {
                GrowMutualFundResponse growMutualFundDetails = growAPIManager.getAllMutualFundDetails(i, 15);
                if (CollectionUtils.isNotEmpty(growMutualFundDetails.getContent())) {
                    for (GrowMutualFundDetails growMutual : growMutualFundDetails.getContent()) {
                        try {
                            mutualFundService.persistGrowMutualFundDetails(growMutual);
                        } catch (Exception e) {
                            log.error("Error occurred while persisting mutual fund: {}", GsonUtils.getGson().toJson(growMutual), e);
                        }
                    }
                }
                Thread.sleep(5000);
            } catch (Exception e) {
                log.error("Error occurred while persisting mutual fund for: {}", i, e);
            }
        }
    }
}
