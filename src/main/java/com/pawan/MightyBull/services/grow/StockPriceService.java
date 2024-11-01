package com.pawan.MightyBull.services.grow;

import com.pawan.MightyBull.dao.StockPriceDao;
import com.pawan.MightyBull.dto.grow.GrowLivePriceDto;
import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.entity.StockPriceEntity;
import com.pawan.MightyBull.mapper.StockPriceMapper;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Slf4j
@Service
public class StockPriceService {

    private final StockPriceDao stockPriceDao;

    @Autowired
    public StockPriceService(StockPriceDao stockPriceDao) {
        this.stockPriceDao = stockPriceDao;
    }

    public void persistGrowStockPriceDetails(GrowStockDetails stockDetails) {
        Optional<StockPriceEntity> stockPriceEntity = stockPriceDao.getByNseScriptCode(stockDetails.getNseScriptCode());
        GrowLivePriceDto livePriceDto = stockDetails.getLivePriceDto();
        if(stockPriceEntity.isEmpty()) {
            StockPriceEntity entity = StockPriceMapper.INSTANCE.mapDtoToEntity(stockDetails.getLivePriceDto());
            stockPriceDao.save(entity);
            log.info("Added new stock price in the system: {}", GsonUtils.getGson().toJson(entity));
        } else {
            StockPriceEntity entity = stockPriceEntity.get();
            entity.setTsInMillis(livePriceDto.getTsInMillis());
            entity.setOpen(livePriceDto.getOpen());
            entity.setHigh(livePriceDto.getHigh());
            entity.setLow(livePriceDto.getLow());
            entity.setClose(livePriceDto.getClose());
            entity.setLtp(livePriceDto.getLtp());
            entity.setDayChange(livePriceDto.getDayChange());
            entity.setDayChangePerc(livePriceDto.getDayChangePerc());
            entity.setLowPriceRange(livePriceDto.getLowPriceRange());
            entity.setHighPriceRange(livePriceDto.getHighPriceRange());
            entity.setVolume(livePriceDto.getVolume());
            entity.setTotalBuyQty(livePriceDto.getTotalBuyQty());
            entity.setTotalSellQty(livePriceDto.getTotalSellQty());
            entity.setOiDayChange(livePriceDto.getOiDayChange());
            entity.setOiDayChangePerc(livePriceDto.getOiDayChangePerc());
            entity.setLastTradeQty(livePriceDto.getLastTradeQty());
            entity.setLastTradeTime(livePriceDto.getLastTradeTime());
            stockPriceDao.save(entity);
            log.info("Updated new stock price in the system: {}", GsonUtils.getGson().toJson(entity));
        }
    }
}
