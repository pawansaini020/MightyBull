package com.pawan.MightyBull.mapper;

import com.pawan.MightyBull.dto.Screener.ScreenerStockDetails;
import com.pawan.MightyBull.entity.ScreenerStockDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Mapper
public interface ScreenerStockDetailsMapper {

    ScreenerStockDetailsMapper INSTANCE = Mappers.getMapper(ScreenerStockDetailsMapper.class);

    ScreenerStockDetailsEntity mapDtoToEntity(ScreenerStockDetails dto);
}
