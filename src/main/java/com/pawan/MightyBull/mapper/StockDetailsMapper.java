package com.pawan.MightyBull.mapper;

import com.pawan.MightyBull.dto.grow.GrowStockDetails;
import com.pawan.MightyBull.entity.StockDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Mapper
public interface StockDetailsMapper {

    StockDetailsMapper INSTANCE = Mappers.getMapper(StockDetailsMapper.class);

    StockDetailsEntity mapDtoToEntity(GrowStockDetails dto);
}
