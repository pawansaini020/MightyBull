package com.pawan.MightyBull.services.screener;

import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.dto.grow.Screener.ScreenerStockDetails;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.mapper.ScreenerStockDetailsMapper;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Slf4j
@Service
public class ScreenerService {

    private final ScreenerStockDetailsDao stockDetailsDao;

    @Autowired
    private ScreenerService(ScreenerStockDetailsDao stockDetailsDao) {
        this.stockDetailsDao = stockDetailsDao;
    }

    public void addStockDetails(String stockId, ScreenerStockDetails stockDetails) {
        Optional<ScreenerStockDetailsEntity> stockDetailsEntity = stockDetailsDao.getByStockId(stockId);
        if (stockDetailsEntity.isEmpty()) {
            ScreenerStockDetailsEntity entity = ScreenerStockDetailsMapper.INSTANCE.mapDtoToEntity(stockDetails);
            stockDetailsDao.save(entity);
            log.info("Added new stock from screener in the system: {}", GsonUtils.getGson().toJson(entity));
        } else {
            ScreenerStockDetailsEntity entity = stockDetailsEntity.get();
            entity.setRequiredDetails(stockDetails);
            stockDetailsDao.save(entity);
            log.info("Updated new stock from screener in the system: {}", GsonUtils.getGson().toJson(entity));
        }
    }
}
