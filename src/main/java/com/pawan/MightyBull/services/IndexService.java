package com.pawan.MightyBull.services;

import com.pawan.MightyBull.dao.IndexDao;
import com.pawan.MightyBull.dto.grow.response.IndexDto;
import com.pawan.MightyBull.dto.index.IndexWidget;
import com.pawan.MightyBull.entity.IndexEntity;
import com.pawan.MightyBull.mapper.IndexMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IndexService {

    private final IndexDao indexDao;

    @Autowired
    public IndexService(IndexDao indexDao) {
        this.indexDao = indexDao;
    }

    public void syncIndex(IndexDto indexDto) {
        Optional<IndexEntity> indexEntity = indexDao.getBySymbol(indexDto.getSymbol());
        if(indexEntity.isPresent()) {
            IndexEntity entity = indexEntity.get();
            entity.setValue(indexDto.getValue());
            entity.setOpen(indexDto.getOpen());
            entity.setClose(indexDto.getClose());
            entity.setDayChange(indexDto.getDayChange());
            entity.setDayChangePerc(indexDto.getDayChangePerc());
            entity.setLow(indexDto.getLow());
            entity.setHigh(indexDto.getHigh());
            entity.setYearLowPrice(indexDto.getYearLowPrice());
            entity.setYearHighPrice(indexDto.getYearHighPrice());
            indexDao.save(entity);
        } else {
            IndexEntity entity = IndexMapper.INSTANCE.mapDtoToEntity(indexDto);
            indexDao.save(entity);
        }
    }

    public List<IndexWidget> getIndexWidgets() {
        List<IndexEntity> entities = indexDao.getAll();
        List<IndexWidget> widgets = new ArrayList<>();

        for(IndexEntity entity : entities) {
            IndexWidget widget = IndexMapper.INSTANCE.mapEntityToWidget(entity);
            widgets.add(widget);
        }
        return widgets;
    }
}
