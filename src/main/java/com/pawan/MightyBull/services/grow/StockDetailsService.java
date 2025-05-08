package com.pawan.MightyBull.services.grow;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.dao.StockDetailsDao;
import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.entity.StockDetailsEntity;
import com.pawan.MightyBull.mapper.StockDetailsMapper;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Slf4j
@Service
public class StockDetailsService {

    private final StockDetailsDao stockDetailsDao;
    private final GrowAPIManager growAPIManager;

    @Autowired
    public StockDetailsService(StockDetailsDao stockDetailsDao,
                               GrowAPIManager growAPIManager) {
        this.stockDetailsDao = stockDetailsDao;
        this.growAPIManager = growAPIManager;
    }

    public void persistGrowStockDetails(GrowStockDetails stockDetails) {
        Optional<StockDetailsEntity> stockDetailsEntity = stockDetailsDao.getByStockId(stockDetails.getStockId());
        if(stockDetailsEntity.isEmpty()) {
            StockDetailsEntity entity = StockDetailsMapper.INSTANCE.mapDtoToEntity(stockDetails);
            stockDetailsDao.save(entity);
            log.info("Added new stock in the system: {}", GsonUtils.getGson().toJson(entity));
        } else {
            StockDetailsEntity entity = stockDetailsEntity.get();
            entity.setCompanyName(stockDetails.getCompanyName());
            entity.setCompanyShortName(stockDetails.getCompanyShortName());
            entity.setYearlyHighPrice(stockDetails.getYearlyHighPrice());
            entity.setYearlyLowPrice(stockDetails.getYearlyLowPrice());
            entity.setClosePrice(stockDetails.getClosePrice());
            entity.setMarketCap(stockDetails.getMarketCap());
            stockDetailsDao.save(entity);
            log.info("Updated new stock in the system: {}", GsonUtils.getGson().toJson(entity));
        }
    }

    public List<String> getAllStockIds() {
        return stockDetailsDao.getAllStockIds();
    }
}
