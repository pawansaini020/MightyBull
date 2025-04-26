package com.pawan.MightyBull.dto.grow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pawan.MightyBull.dto.mutualfund.MutualFundReturnStatsDto;
import com.pawan.MightyBull.dto.mutualfund.MutualFundStockHoldingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowMutualFundDetails {

    @JsonProperty(value = "search_id")
    private String mutualFundId;

    @JsonProperty(value = "benchmark_name")
    private String benchmarkName;

    @JsonProperty(value = "meta_desc")
    private String metaDesc;

    @JsonProperty(value = "nav")
    private Double nav;

    @JsonProperty(value = "nav_date")
    private String navDate;

    @JsonProperty(value = "launch_date")
    private String launchDate;

    @JsonProperty(value = "exit_load")
    private String exitLoadMessage;

    @JsonProperty(value = "expense_ratio")
    private Double expenseRatio;

    @JsonProperty(value = "stamp_duty")
    private String stampDuty;

    @JsonProperty(value = "dividend")
    private Double dividend;

    @JsonProperty(value = "analysis")
    private List<Map<String, Object>> analysis;

    @JsonProperty(value = "amc_info")
    private Map<String, Object> amcInfo;

    @JsonProperty(value = "return_stats")
    private List<MutualFundReturnStatsDto> returnStats;

    @JsonProperty(value = "holdings")
    private List<MutualFundStockHoldingDto> holdings;

    @JsonProperty(value = "lock_in")
    private Map<String, Double> lockIn;
}
