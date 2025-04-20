package com.pawan.MightyBull.mapper;

import com.pawan.MightyBull.dto.grow.GrowMutualFundDetails;
import com.pawan.MightyBull.dto.mutualfund.MutualFundWidgetDto;
import com.pawan.MightyBull.entity.MutualFundEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MutualFundMapper {

    MutualFundMapper INSTANCE = Mappers.getMapper(MutualFundMapper.class);

    MutualFundEntity mapDetailsToEntity(GrowMutualFundDetails details);

    MutualFundWidgetDto mapEntityToWidget(MutualFundEntity entity);
}
