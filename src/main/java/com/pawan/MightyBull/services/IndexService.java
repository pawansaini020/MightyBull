package com.pawan.MightyBull.services;

import com.pawan.MightyBull.Managers.GrowAPIManager;
import com.pawan.MightyBull.dao.IndexDao;
import com.pawan.MightyBull.dto.grow.request.GrowIndexDetails;
import com.pawan.MightyBull.dto.grow.response.IndexDto;
import com.pawan.MightyBull.dto.index.IndexWidget;
import com.pawan.MightyBull.dto.index.IndexWidgetDetails;
import com.pawan.MightyBull.entity.IndexEntity;
import com.pawan.MightyBull.enums.IndexType;
import com.pawan.MightyBull.mapper.IndexMapper;
import com.pawan.MightyBull.utils.StockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IndexService {

    private final IndexDao indexDao;
    private final GrowAPIManager growAPIManager;

    @Autowired
    public IndexService(IndexDao indexDao,
                        GrowAPIManager growAPIManager) {
        this.indexDao = indexDao;
        this.growAPIManager = growAPIManager;
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
            if(entity.getType() == IndexType.INDIAN) {
                entity.setCompanies(getCompanies(entity.getIndexId()));
            }
            indexDao.save(entity);
        } else {
            IndexEntity entity = IndexMapper.INSTANCE.mapDtoToEntity(indexDto);
            indexDao.save(entity);
        }
    }

    public List<IndexWidget> getIndexWidgets(IndexType type) {
        List<IndexEntity> entities = indexDao.getByType(type);
        List<IndexWidget> widgets = new ArrayList<>();

        for(IndexEntity entity : entities) {
            IndexWidget widget = IndexMapper.INSTANCE.mapEntityToWidget(entity);
            widgets.add(widget);
        }
        return widgets;
    }

    private List<String> getCompanies(String indexId) {
        try {
            GrowIndexDetails indexDetails = growAPIManager.getIndexDetails(indexId);
            if(CollectionUtils.isNotEmpty(indexDetails.getChildAssets())) {
                return indexDetails.getChildAssets().stream()
                        .map(dto -> {
                            if(dto.getHeader()!=null) {
                                return StockUtils.getStockId(dto.getHeader().getBseScriptCode(), dto.getHeader().getNseScriptCode());
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("Error occurred while persisting stock for: {}", indexId, e);
        }
        return new ArrayList<>();
    }

    public IndexWidgetDetails getIndexWidgetDetails(String indexId) {
        IndexWidgetDetails widget = new IndexWidgetDetails();
        Optional<IndexEntity> entity = indexDao.getByIndexId(indexId);
        if(entity.isPresent()) {
            widget = IndexMapper.INSTANCE.mapEntityToWidgetDetails(entity.get());
        }
        return widget;
    }
}
