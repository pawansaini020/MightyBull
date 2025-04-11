package com.pawan.MightyBull.services.stock;

import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.dao.StockScoreDao;
import com.pawan.MightyBull.dto.response.PaginationResponse;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.dto.stock.StockSearchDto;
import com.pawan.MightyBull.dto.stock.StockWidgetDetailsDto;
import com.pawan.MightyBull.dto.stock.StockWidgetDto;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.entity.StockScoreEntity;
import com.pawan.MightyBull.mapper.StockScoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockService {

    private final ScreenerStockDetailsDao stockDetailsDao;
    private final StockScoreDao stockScoreDao;

    @Autowired
    public StockService(ScreenerStockDetailsDao stockDetailsDao,
                        StockScoreDao stockScoreDao) {
        this.stockDetailsDao = stockDetailsDao;
        this.stockScoreDao = stockScoreDao;
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
                    .score(stockDetailsEntity.getScore())
                    .prosList(List.of("Company has been maintaining a healthy dividend payout of 30.8%"))
                    .consList(List.of("Stock is trading at 3.12 times its book value",
                            "Company has low interest coverage ratio.",
                            "Tax rate seems low",
                            "Company might be capitalizing the interest cost",
                            "Company has high debtors of 3,557 days."))
                    .quarterlyResults(stockDetailsEntity.getQuarterlyResults())
                    .profitAndLoss(stockDetailsEntity.getProfitAndLoss())
                    .balanceSheet(stockDetailsEntity.getBalanceSheet())
                    .ratios(stockDetailsEntity.getRatios())
                    .shareholdingPattern(stockDetailsEntity.getShareholdingPattern());
            StockScoreEntity scoreEntity = stockScoreDao.getByStockId(stockId).orElse(null);
            detailsDtoBuilder.scoreDTO(StockScoreMapper.INSTANCE.mapEntityToDto(scoreEntity));
        }
        return detailsDtoBuilder.build();
    }

    public List<StockSearchDto> searchStocks(String stockName) {
        List<ScreenerStockDetailsEntity> entities = stockDetailsDao.getStocksByName(stockName);
        entities.addAll(stockDetailsDao.getStocksByStockId(stockName));
        List<StockSearchDto> stockSearchList = new ArrayList<>();
        Set<String> stockSet = new HashSet<>();
        for(ScreenerStockDetailsEntity entity : entities) {
            if(!stockSet.contains(entity.getStockId())) {
                stockSearchList.add(new StockSearchDto(entity.getStockId(), entity.getName()));
                stockSet.add(entity.getStockId());
            }
        }
        return stockSearchList;
    }
}
