package com.pawan.MightyBull.dto.mutualfund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MutualFundReturnStatsDto {

    private Double return1d;
    private Double return1w;
    private Double return1m;
    private Double return3m;
    private Double return6m;
    private Double return9m;
    private Double return1y;
    private Double return2y;
    private Double return3y;
    private Double return4y	;
    private Double return5y;
    private Double return10y;

    @JsonProperty(value = "cat_return3m")
    private Double catReturn3m;

    @JsonProperty(value = "cat_return6m")
    private Double catReturn6m;

    @JsonProperty(value = "cat_return1y")
    private Double catReturn1y;

    @JsonProperty(value = "cat_return3y")
    private Double catReturn3y;

    @JsonProperty(value = "cat_return5y")
    private Double catReturn5y;

    private Double rank3m;
    private Double rank6m;
    private Double rank1yr;
    private Double rank3yr;
    private Double rank5yr;

    @JsonProperty(value = "return_default")
    private Double returnDefault;

    @JsonProperty(value = "mean_return")
    private Double meanReturn;

    @JsonProperty(value = "return_since_created")
    private Double returnSinceCreated;

    @JsonProperty(value = "beta")
    private Double beta;

    @JsonProperty(value = "alpha")
    private Double alpha;

    @JsonProperty(value = "sortino_ratio")
    private Double sortingRatio;

    @JsonProperty(value = "information_ratio")
    private Double informationRatio;

    @JsonProperty(value = "sharpe_ratio")
    private Double sharpeRatio;

    @JsonProperty(value = "standard_deviation")
    private Double standardDeviation;

}
