package com.pawan.MightyBull.services;

import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.dao.StockScoreDao;
import com.pawan.MightyBull.dto.score.StockScoreDTO;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.entity.StockScoreEntity;
import com.pawan.MightyBull.mapper.StockScoreMapper;
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
    private StockScoreDao stockScoreDao;

    @Autowired
    private ScoringService(ScreenerStockDetailsDao screenerStockDetailsDao,
                           FundamentalAnalysisService fundamentalAnalysisService,
                           StockScoreDao stockScoreDao) {
        this.screenerStockDetailsDao = screenerStockDetailsDao;
        this.fundamentalAnalysisService = fundamentalAnalysisService;
        this.stockScoreDao = stockScoreDao;
    }

    public StockScoreDTO generateStockScore(String stockId) {
        try {
            Optional<ScreenerStockDetailsEntity> stockDetailsEntity = screenerStockDetailsDao.getByStockId(stockId);
            if (stockDetailsEntity.isPresent()) {
                StockScoreDTO scoreInfo = fundamentalAnalysisService.calculateScore(stockDetailsEntity.get());
                syncStockScore(scoreInfo);
                return scoreInfo;
            }
        } catch (Exception e) {
            log.error("Error occurred while generating stock score for stockId: {}", stockId);
        }
        return new StockScoreDTO();
    }

    public void syncStockScore(StockScoreDTO scoreInfo) {
        Optional<StockScoreEntity> stockScoreEntity = stockScoreDao.getByStockId(scoreInfo.getStockId());
        StockScoreEntity entity = null;
        if(stockScoreEntity.isPresent()) {
            entity = stockScoreEntity.get();
            scoreInfo.updateScore(entity);
        } else {
            entity = StockScoreMapper.INSTANCE.mapDtoToEntity(scoreInfo);
        }
        stockScoreDao.save(entity);
    }
}
