package com.pawan.MightyBull.mapper;

import com.pawan.MightyBull.dto.grow.response.IndexDto;
import com.pawan.MightyBull.dto.index.IndexWidget;
import com.pawan.MightyBull.entity.IndexEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IndexMapper {

    IndexMapper INSTANCE = Mappers.getMapper(IndexMapper.class);

    IndexEntity mapDtoToEntity(IndexDto dto);

    IndexDto mapEntityToDto(IndexEntity entity);

    IndexWidget mapEntityToWidget(IndexEntity entity);
}
