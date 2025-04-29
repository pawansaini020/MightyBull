package com.pawan.MightyBull.dto.mutualfund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class MutualFundWidgetDto {

    private String mutualFundId;
    private String name;
    private String fundHouse;
    private String category;
    private String subCategory;
    private String risk;
    private Double riskRating;
    private String logoUrl;
    private Double return1d;
    private Double return1y;
    private Double return3y;
    private Double return5y;
    private Double aum;
}
