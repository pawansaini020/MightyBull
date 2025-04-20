package com.pawan.MightyBull.dto.grow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowMutualFundDetails {

    @JsonProperty(value = "id")
    private String mutualFundId;

    @JsonProperty(value = "direct_scheme_name")
    private String name;

    @JsonProperty(value = "fund_house")
    private String fundHouse;

    @JsonProperty(value = "fund_manager")
    private String fundManager;

    @JsonProperty(value = "amc")
    private String amc;

    @JsonProperty(value = "plan_type")
    private String planType;

    @JsonProperty(value = "category")
    private String category;

    @JsonProperty(value = "sub_category")
    private String subCategory;

    @JdbcTypeCode(SqlTypes.JSON)
    @JsonProperty(value = "sub_sub_category")
    private List<String> subSubCategory;

    @JsonProperty(value = "risk")
    private String risk;

    @JsonProperty(value = "risk_rating")
    private Double riskRating;

    @JsonProperty(value = "index")
    private Boolean index;

    @JsonProperty(value = "logo_url")
    private String logoUrl;

    @JsonProperty(value = "return1d")
    private Double return1d;

    @JsonProperty(value = "return1y")
    private Double return1y;

    @JsonProperty(value = "return3y")
    private Double return3y;

    @JsonProperty(value = "return5y")
    private Double return5y;

    @JsonProperty(value = "min_investment_amount")
    private Long minInvestmentAmount;

    @JsonProperty(value = "min_sip_investment")
    private Long minSipInvestment;

    @JsonProperty(value = "aum")
    private Double aum;
}
