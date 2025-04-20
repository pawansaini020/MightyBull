package com.pawan.MightyBull.services.mutualfund;

import com.pawan.MightyBull.dao.MutualFundDao;
import com.pawan.MightyBull.dto.grow.GrowMutualFundDetails;
import com.pawan.MightyBull.dto.mutualfund.MutualFundWidgetDto;
import com.pawan.MightyBull.dto.response.PaginationResponse;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.dto.stock.StockWidgetDto;
import com.pawan.MightyBull.entity.MutualFundEntity;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import com.pawan.MightyBull.mapper.MutualFundMapper;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MutualFundService {

    private final MutualFundDao mutualFundDao;

    @Autowired
    public MutualFundService(MutualFundDao mutualFundDao) {
        this.mutualFundDao = mutualFundDao;
    }

    public void persistGrowMutualFundDetails(GrowMutualFundDetails mutualFundDetails) {
        Optional<MutualFundEntity> mutualFundEntity = mutualFundDao.getByMutualFundId(mutualFundDetails.getMutualFundId());
        if(mutualFundEntity.isEmpty()) {
            MutualFundEntity entity = MutualFundMapper.INSTANCE.mapDetailsToEntity(mutualFundDetails);
            mutualFundDao.save(entity);
            log.info("Added new mutual fund in the system: {}", GsonUtils.getGson().toJson(entity));
        } else {
            MutualFundEntity entity = mutualFundEntity.get();
            entity.setName(mutualFundDetails.getName());
            entity.setFundManager(mutualFundDetails.getFundManager());
            entity.setRiskRating(mutualFundDetails.getRiskRating());
            entity.setReturn1d(mutualFundDetails.getReturn1d());
            entity.setReturn1y(mutualFundDetails.getReturn1y());
            entity.setReturn3y(mutualFundDetails.getReturn3y());
            entity.setReturn5y(mutualFundDetails.getReturn5y());
            entity.setMinSipInvestment(mutualFundDetails.getMinSipInvestment());
            entity.setMinInvestmentAmount(mutualFundDetails.getMinInvestmentAmount());
            mutualFundDao.save(entity);
            log.info("Updated new mutual fund in the system: {}", GsonUtils.getGson().toJson(entity));
        }
    }

    public SuccessResponse<?> getMutualFundWidgets(Integer pageNumber, Integer pageSize) {
        Page<MutualFundEntity> entities = mutualFundDao.getFilteredEntity(pageNumber, pageSize);
        List<MutualFundWidgetDto> mutualFunds = entities.getContent().stream()
                .map(this::buildMutualFundWidgets)
                .collect(Collectors.toList());
        return new SuccessResponse<>(mutualFunds, new PaginationResponse(entities));
    }

    private MutualFundWidgetDto buildMutualFundWidgets(MutualFundEntity mutualFundEntity) {
        return MutualFundMapper.INSTANCE.mapEntityToWidget(mutualFundEntity);
    }
}
