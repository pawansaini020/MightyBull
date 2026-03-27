package com.pawan.MightyBull.entity.mongo;

import com.pawan.MightyBull.entity.base.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "stock_score_info")
public class StockScoreDocument extends BaseDocument<Long> {

    @Indexed
    private String stockId;

    private Double score;

    private Double marketCapScore;

    private Double priceScore;

    private Double peScore;

    private Double dividendYieldScore;

    private Double roceScore;

    private Double rocScore;

    private Double quarterlyProfitScore;

    private Double profitAndLossScore;

    private Double balanceSheetScore;

    private Double cashFlowScore;

    private Double debtorDaysScore;

    private Double yearlyRoceScore;

    private Double shareholdingPatternScore;
}
