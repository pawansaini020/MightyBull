package com.pawan.MightyBull.entity.mongo;

import com.pawan.MightyBull.dto.mutualfund.MutualFundReturnStatsDto;
import com.pawan.MightyBull.dto.mutualfund.MutualFundStockHoldingDto;
import com.pawan.MightyBull.entity.base.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "mutual_fund_details")
public class MutualFundDetailsDocument extends BaseDocument<Long> {

    @Indexed
    private String mutualFundId;

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
}
