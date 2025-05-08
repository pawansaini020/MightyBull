package com.pawan.MightyBull.dto.mutualfund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pawan.MightyBull.entity.MutualFundDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MutualFundWidgetDetailsDto {

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
    private String benchmarkName;
    private String metaDesc;
    private Double rank;
    private Double nav;
    private String navDate;
    private String launchDate;
    private String exitLoadMessage;
    private Double expenseRatio;
    private String stampDuty;
    private Double dividend;
    private Map<String, String> analysis;
    private List<MutualFundReturnStatsDto> returnStats;
    private List<MutualFundStockHoldingDto> holdings;
    private Map<String, Double> lockIn;

    public void setEntityDetails(MutualFundDetailsEntity mutualFundDetailsEntity) {
        this.benchmarkName = mutualFundDetailsEntity.getBenchmarkName();
        this.metaDesc = mutualFundDetailsEntity.getMetaDesc();
        this.rank = mutualFundDetailsEntity.getRank();
        this.nav = mutualFundDetailsEntity.getNav();
        this.navDate = mutualFundDetailsEntity.getNavDate();
        this.launchDate = mutualFundDetailsEntity.getLaunchDate();
        this.exitLoadMessage = mutualFundDetailsEntity.getExitLoadMessage();
        this.expenseRatio = mutualFundDetailsEntity.getExpenseRatio();
        this.stampDuty = mutualFundDetailsEntity.getStampDuty();
        this.dividend = mutualFundDetailsEntity.getDividend();
        this.analysis = mutualFundDetailsEntity.getAnalysis();
        this.returnStats = mutualFundDetailsEntity.getReturnStats();
        this.holdings = mutualFundDetailsEntity.getHoldings();
        this.lockIn = mutualFundDetailsEntity.getLockIn();
    }
}
