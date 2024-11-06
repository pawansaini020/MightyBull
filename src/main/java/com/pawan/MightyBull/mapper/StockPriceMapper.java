package com.pawan.MightyBull.mapper;

import com.pawan.MightyBull.dto.grow.GrowLivePriceDto;
import com.pawan.MightyBull.entity.StockPriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Mapper
public interface StockPriceMapper {

    StockPriceMapper INSTANCE = Mappers.getMapper(StockPriceMapper.class);

    StockPriceEntity mapDtoToEntity(GrowLivePriceDto dto);
}
