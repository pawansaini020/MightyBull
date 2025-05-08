package com.pawan.MightyBull.dto.grow.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class GrowIndexResponse {

    private String source;
    private Map<String, Map<String, Map<String, IndexDto>>> exchangeAggRespMap;
    private List<IndexResponse> aggregatedGlobalInstrumentDto;
}
