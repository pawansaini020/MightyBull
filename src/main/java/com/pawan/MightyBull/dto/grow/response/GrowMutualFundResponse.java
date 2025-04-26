package com.pawan.MightyBull.dto.grow.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pawan.MightyBull.dto.grow.GrowMutualFund;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowMutualFundResponse {

    @JsonProperty("content")
    private List<GrowMutualFund> content;

    @JsonProperty("total_results")
    private Integer totalResults;
}
