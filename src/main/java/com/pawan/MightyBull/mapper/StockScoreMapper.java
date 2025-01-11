package com.pawan.MightyBull.mapper;

import com.pawan.MightyBull.dto.score.StockScoreDTO;
import com.pawan.MightyBull.entity.StockScoreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Pawan Saini
 * Created on 11/01/25.
 */
@Mapper
public interface StockScoreMapper {

    StockScoreMapper INSTANCE = Mappers.getMapper(StockScoreMapper.class);

    StockScoreEntity mapDtoToEntity(StockScoreDTO dto);
}
