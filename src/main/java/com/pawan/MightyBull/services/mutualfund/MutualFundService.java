package com.pawan.MightyBull.services.mutualfund;

import com.pawan.MightyBull.dao.MutualFundDao;
import com.pawan.MightyBull.dto.grow.GrowMutualFundDetails;
import com.pawan.MightyBull.entity.MutualFundEntity;
import com.pawan.MightyBull.entity.StockDetailsEntity;
import com.pawan.MightyBull.mapper.MutualFundMapper;
import com.pawan.MightyBull.mapper.StockDetailsMapper;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
