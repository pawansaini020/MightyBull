package com.pawan.MightyBull.services;

import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.services.analysis.FundamentalAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 07/11/24.
 */
@Slf4j
@Service
public class ScoringService {

    private ScreenerStockDetailsDao screenerStockDetailsDao;
    private FundamentalAnalysisService fundamentalAnalysisService;

    @Autowired
    private ScoringService(ScreenerStockDetailsDao screenerStockDetailsDao,
                           FundamentalAnalysisService fundamentalAnalysisService) {
        this.screenerStockDetailsDao = screenerStockDetailsDao;
        this.fundamentalAnalysisService = fundamentalAnalysisService;
    }

    public int generateStockScore(String stockId) {
        Optional<ScreenerStockDetailsEntity> stockDetailsEntity = screenerStockDetailsDao.getByStockId(stockId);
        if(stockDetailsEntity.isPresent()) {
            return fundamentalAnalysisService.calculateScore(stockDetailsEntity.get());
        }
        return 0;
    }
}
