package com.pawan.MightyBull.dto.grow.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class GrowStockRequest {

    private Map<String, List<String>> listFilters;
    private Map<String, Map<String, Long>> objFilters;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortType;
}
