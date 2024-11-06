package com.pawan.MightyBull.services.reporting;

import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Pawan Saini
 * Created on 05/11/24.
 */
@Slf4j
@Service
public class AnalysisService {

    private final ScreenerStockDetailsDao screenerStockDetailsDao;

    @Autowired
    private AnalysisService(ScreenerStockDetailsDao screenerStockDetailsDao) {
        this.screenerStockDetailsDao = screenerStockDetailsDao;
    }

    public TreeMap<Integer, List<String>> stockAnalysis() {
        List<ScreenerStockDetailsEntity> entities = screenerStockDetailsDao.getAll();
        TreeMap<Integer, List<String>> stockRecommendation = new TreeMap<>();
        for(ScreenerStockDetailsEntity entity : entities) {
            int profitX = checkProfitX(entity);
            if(profitX != 0 && checkShareholdingPattern(entity)) {
                if(!stockRecommendation.containsKey(profitX)) {
                    stockRecommendation.put(profitX, new ArrayList<>());
                }
                stockRecommendation.get(profitX).add(entity.getName());
            }
        }
        return stockRecommendation;
    }

    private int checkProfitX(ScreenerStockDetailsEntity entity) {
        try {
            Map<String, Map<String, Double>> profitAndLoss = entity.getProfitAndLoss();
            if(profitAndLoss!=null && profitAndLoss.get("Net Profit") != null) {
                Map<String, Double> netProfit = profitAndLoss.get("Net Profit");
                if(netProfit!=null) {
                    Double Mar2013 = netProfit.get("Mar 2013");
                    if(Mar2013 == null || Mar2013 <=0) {
                        Mar2013 = netProfit.get("Mar 2014");
                    }
                    if(Mar2013 == null || Mar2013 <=0) {
                        Mar2013 = netProfit.get("Mar 2015");
                    }
                    if(Mar2013 == null || Mar2013 <=0) {
                        Mar2013 = netProfit.get("Mar 2016");
                    }
                    Double Mar2024 = netProfit.get("Mar 2024");
                    if (Mar2013 != null && Mar2024 != null && Mar2013 != 0 && Mar2024 > 0) {
                        if(Mar2013 > 0) {
                            int profitX = (int) (Mar2024 / Mar2013);
                            if (profitX > 10) {
                                Double Mar2014 = netProfit.getOrDefault("Mar 2014", 0d);
                                Double Mar2015 = netProfit.getOrDefault("Mar 2015", 0d);
                                Double Mar2016 = netProfit.getOrDefault("Mar 2016", 0d);
                                Double Mar2017 = netProfit.getOrDefault("Mar 2017", 0d);
                                Double Mar2018 = netProfit.getOrDefault("Mar 2018", 0d);
                                Double Mar2019 = netProfit.getOrDefault("Mar 2019", 0d);
                                Double Mar2020 = netProfit.getOrDefault("Mar 2020", 0d);
                                Double Mar2021 = netProfit.getOrDefault("Mar 2021", 0d);
                                Double Mar2022 = netProfit.getOrDefault("Mar 2022", 0d);
                                Double Mar2023 = netProfit.getOrDefault("Mar 2023", 0d);
                                if(Mar2013 <= Mar2014*1.25 && Mar2014 <= Mar2015*1.25 && Mar2015 <= Mar2016*1.25 && Mar2016 <= Mar2017*1.25 && Mar2017 <= Mar2018*1.25 && Mar2018 <= Mar2019*1.25 && Mar2019 <= Mar2020*1.25 && Mar2020 <= Mar2021*1.25 && Mar2021 <= Mar2022*1.25 && Mar2022 <= Mar2023*1.25 && Mar2023 <= Mar2024*1.25) {
                                    return profitX;
                                }
                            }
                        } else if(Mar2024 > 100) {
                            Double Mar2014 = netProfit.getOrDefault("Mar 2014", 0d);
                            Double Mar2015 = netProfit.getOrDefault("Mar 2015", 0d);
                            Double Mar2016 = netProfit.getOrDefault("Mar 2016", 0d);
                            Double Mar2017 = netProfit.getOrDefault("Mar 2017", 0d);
                            Double Mar2018 = netProfit.getOrDefault("Mar 2018", 0d);
                            Double Mar2019 = netProfit.getOrDefault("Mar 2019", 0d);
                            Double Mar2020 = netProfit.getOrDefault("Mar 2020", 0d);
                            Double Mar2021 = netProfit.getOrDefault("Mar 2021", 0d);
                            Double Mar2022 = netProfit.getOrDefault("Mar 2022", 0d);
                            Double Mar2023 = netProfit.getOrDefault("Mar 2023", 0d);
                            if(Mar2013 <= Mar2014*1.25 && Mar2014 <= Mar2015*1.25 && Mar2015 <= Mar2016*1.25 && Mar2016 <= Mar2017*1.25 && Mar2017 <= Mar2018*1.25 && Mar2018 <= Mar2019*1.25 && Mar2019 <= Mar2020*1.25 && Mar2020 <= Mar2021*1.25 && Mar2021 <= Mar2022*1.25 && Mar2022 <= Mar2023*1.25 && Mar2023 <= Mar2024*1.25) {
                                return (int) Math.round(Mar2024);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while checking stocks recommended for: {}, {}", entity.getName(), entity.getName());
        }
        return 0;
    }


    private boolean checkShareholdingPattern(ScreenerStockDetailsEntity entity) {
        try {
            Map<String, Map<String, Double>> shareholdingPattern = entity.getShareholdingPattern();
            if (shareholdingPattern != null && shareholdingPattern.get("Promoters") != null && shareholdingPattern.get("DIIs") != null && shareholdingPattern.get("FIIs") != null) {
                if(shareholdingPattern.get("DIIs").get("Sep 2024") != 0 && shareholdingPattern.get("FIIs").get("Sep 2024") !=0) {
                    double shareholding = shareholdingPattern.get("Promoters").get("Sep 2024") + shareholdingPattern.get("DIIs").get("Sep 2024") + shareholdingPattern.get("FIIs").get("Sep 2024");
                    if(shareholding > 60) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while checking stocks shareholding pattern for: {}, {}", entity.getName(), entity.getName());
        }
        return false;
    }

    public LinkedHashMap<String, Double> highDividendStocks(Integer pageNumber, Integer pageSize) {
        Page<ScreenerStockDetailsEntity> entityList = screenerStockDetailsDao.getStockByDividend(pageNumber, pageSize);
        LinkedHashMap<String, Double> highDividendStocks = new LinkedHashMap<>();
        for(ScreenerStockDetailsEntity entity : entityList.getContent()) {
            highDividendStocks.put(entity.getName(), entity.getDividendYield());
        }
        return highDividendStocks;
    }
}
