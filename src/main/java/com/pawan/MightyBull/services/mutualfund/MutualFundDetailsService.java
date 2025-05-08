package com.pawan.MightyBull.services.mutualfund;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.dao.MutualFundDao;
import com.pawan.MightyBull.dao.MutualFundDetailsDao;
import com.pawan.MightyBull.dao.ScreenerStockDetailsDao;
import com.pawan.MightyBull.dto.grow.GrowMutualFundDetails;
import com.pawan.MightyBull.dto.mutualfund.MutualFundWidgetDetailsDto;
import com.pawan.MightyBull.dto.mutualfund.MutualFundWidgetDto;
import com.pawan.MightyBull.entity.MutualFundDetailsEntity;
import com.pawan.MightyBull.entity.MutualFundEntity;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.mapper.MutualFundMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MutualFundDetailsService {

    private final MutualFundDetailsDao mutualFundDetailsDao;
    private final MutualFundDao mutualFundDao;
    private final GrowAPIManager growAPIManager;
    private final ScreenerStockDetailsDao stockDetailsDao;

    @Autowired
    public MutualFundDetailsService(MutualFundDetailsDao mutualFundDetailsDao,
                                    MutualFundDao mutualFundDao,
                                    GrowAPIManager growAPIManager,
                                    ScreenerStockDetailsDao stockDetailsDao) {
        this.mutualFundDetailsDao = mutualFundDetailsDao;
        this.mutualFundDao = mutualFundDao;
        this.growAPIManager = growAPIManager;
        this.stockDetailsDao = stockDetailsDao;
    }

    public void syncMutualFundDetails(String mutualFundId) {
        try {
            GrowMutualFundDetails mutualFundDetails = growAPIManager.getMutualFundDetails(mutualFundId);
            if (mutualFundDetails != null && StringUtils.isNotBlank(mutualFundDetails.getMutualFundId())) {

                if(CollectionUtils.isNotEmpty(mutualFundDetails.getHoldings())) {
                    mutualFundDetails.getHoldings().forEach(holding -> {
                        String name = holding.getCompanyName();
                        if (StringUtils.isNotBlank(name)) {
                            if(name.charAt(name.length()-1) == '.') {
                                name = name.substring(0, name.length()-1);
                                holding.setCompanyName(name);
                            }
                            Optional<ScreenerStockDetailsEntity> entity = stockDetailsDao.getByName(name);
                            entity.ifPresent(screenerStockDetailsEntity -> holding.setStockId(screenerStockDetailsEntity.getStockId()));
                        }
                    });
                }

                Optional<MutualFundDetailsEntity> existingEntity = mutualFundDetailsDao.getByMutualFundId(mutualFundId);
                Double rank = mutualFundDetails.getAmcInfo() != null && mutualFundDetails.getAmcInfo().get("rank") !=null ? Double.valueOf((String) mutualFundDetails.getAmcInfo().get("rank")) : null;
                Map<String, String> analysis = mutualFundDetails.getAnalysis().stream()
                        .filter(map -> map.containsKey("analysis_subject"))
                        .collect(Collectors.toMap(
                                map -> (String) map.get("analysis_subject"),
                                map -> (String) map.get("analysis_desc")
                        ));
                if(existingEntity.isEmpty()) {
                    MutualFundDetailsEntity entity = MutualFundDetailsEntity.builder()
                            .mutualFundId(mutualFundId)
                            .benchmarkName(mutualFundDetails.getBenchmarkName())
                            .metaDesc(mutualFundDetails.getMetaDesc())
                            .rank(rank)
                            .nav(mutualFundDetails.getNav())
                            .navDate(mutualFundDetails.getNavDate())
                            .launchDate(mutualFundDetails.getLaunchDate())
                            .exitLoadMessage(mutualFundDetails.getExitLoadMessage())
                            .expenseRatio(mutualFundDetails.getExpenseRatio())
                            .stampDuty(mutualFundDetails.getStampDuty())
                            .dividend(mutualFundDetails.getDividend())
                            .analysis(analysis)
                            .returnStats(mutualFundDetails.getReturnStats())
                            .holdings(mutualFundDetails.getHoldings())
                            .lockIn(mutualFundDetails.getLockIn())
                            .build();
                    mutualFundDetailsDao.save(entity);
                } else {
                    MutualFundDetailsEntity entity = existingEntity.get();
                    entity.setRank(rank);
                    entity.setNav(mutualFundDetails.getNav());
                    entity.setNavDate(mutualFundDetails.getNavDate());
                    entity.setLaunchDate(mutualFundDetails.getLaunchDate());
                    entity.setExitLoadMessage(mutualFundDetails.getExitLoadMessage());
                    entity.setExpenseRatio(mutualFundDetails.getExpenseRatio());
                    entity.setStampDuty(mutualFundDetails.getStampDuty());
                    entity.setDividend(mutualFundDetails.getDividend());
                    entity.setAnalysis(analysis);
                    entity.setReturnStats(mutualFundDetails.getReturnStats());
                    entity.setHoldings(mutualFundDetails.getHoldings());
                    entity.setLockIn(mutualFundDetails.getLockIn());
                    mutualFundDetailsDao.save(entity);
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching grow mutual fund details: {}", mutualFundId);
        }
    }

    public MutualFundWidgetDetailsDto getMutualFundWidgetDetails(String mutualFundId) {
        MutualFundWidgetDetailsDto widgetDto = new MutualFundWidgetDetailsDto();
        Optional<MutualFundEntity> entity = mutualFundDao.getByMutualFundId(mutualFundId);
        if(entity.isPresent()) {
            widgetDto = MutualFundMapper.INSTANCE.mapEntityToWidgetDetails(entity.get());
            Optional<MutualFundDetailsEntity> detailsEntity = mutualFundDetailsDao.getByMutualFundId(mutualFundId);
            if(detailsEntity.isPresent()) {
                widgetDto.setEntityDetails(detailsEntity.get());
            }
        }
        return widgetDto;
    }
}
