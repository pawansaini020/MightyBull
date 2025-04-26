package com.pawan.MightyBull.mapper;

import com.pawan.MightyBull.dto.grow.GrowMutualFund;
import com.pawan.MightyBull.dto.mutualfund.MutualFundWidgetDetailsDto;
import com.pawan.MightyBull.dto.mutualfund.MutualFundWidgetDto;
import com.pawan.MightyBull.entity.MutualFundEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MutualFundMapper {

    MutualFundMapper INSTANCE = Mappers.getMapper(MutualFundMapper.class);

    MutualFundEntity mapDetailsToEntity(GrowMutualFund details);
    MutualFundWidgetDto mapEntityToWidget(MutualFundEntity entity);
    MutualFundWidgetDetailsDto mapEntityToWidgetDetails(MutualFundEntity entity);
}
