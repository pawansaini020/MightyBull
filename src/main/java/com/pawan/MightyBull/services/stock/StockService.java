package com.pawan.MightyBull.services.stock;

import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.dto.response.PaginationResponse;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.dto.stock.StockWidgetDetailsDto;
import com.pawan.MightyBull.dto.stock.StockWidgetDto;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockService {

    private final ScreenerStockDetailsDao stockDetailsDao;

    @Autowired
    public StockService(ScreenerStockDetailsDao stockDetailsDao) {
        this.stockDetailsDao = stockDetailsDao;
    }

    public SuccessResponse<?> getStockWidgets(List<String> scoreRange, List<String> sortBy, Integer pageNumber, Integer pageSize) {
        Page<ScreenerStockDetailsEntity> entities = stockDetailsDao.getFilteredStocks(scoreRange, sortBy, pageNumber, pageSize);
        List<StockWidgetDto> stockWidgetDtos = entities.getContent().stream()
                .map(this::buildStockWidgetDto)
                .collect(Collectors.toList());
        return new SuccessResponse<>(stockWidgetDtos, new PaginationResponse(entities));
    }

    private StockWidgetDto buildStockWidgetDto(ScreenerStockDetailsEntity entity) {
        return StockWidgetDto.builder()
                .stockId(entity.getStockId())
                .name(entity.getName())
                .sector(entity.getSector())
                .score(entity.getScore())
                .closePrice(entity.getCurrentPrice())
                .yearlyHighPrice(entity.getHigh())
                .yearlyLowPrice(entity.getLow())
                .marketCap(entity.getMarketCap())
                .dividend(entity.getDividendYield())
                .build();
    }

    public StockWidgetDetailsDto getStockWidgetDetails(String stockId) {
        StockWidgetDetailsDto.StockWidgetDetailsDtoBuilder detailsDtoBuilder = StockWidgetDetailsDto.builder();
        ScreenerStockDetailsEntity stockDetailsEntity = stockDetailsDao.getByStockId(stockId).orElse(null);
        if(stockDetailsEntity != null) {
            detailsDtoBuilder.stockId(stockDetailsEntity.getStockId())
                    .name(stockDetailsEntity.getName())
                    .sector(stockDetailsEntity.getSector())
                    .industry(stockDetailsEntity.getIndustry())
                    .marketCap(stockDetailsEntity.getMarketCap())
                    .currentPrice(stockDetailsEntity.getCurrentPrice())
                    .high(stockDetailsEntity.getHigh())
                    .low(stockDetailsEntity.getLow())
                    .stockPE(stockDetailsEntity.getStockPE())
                    .dividendYield(stockDetailsEntity.getDividendYield())
                    .roce(stockDetailsEntity.getRoce())
                    .roe(stockDetailsEntity.getRoe())
                    .score(stockDetailsEntity.getScore());
        }
        return detailsDtoBuilder.build();
    }
}
