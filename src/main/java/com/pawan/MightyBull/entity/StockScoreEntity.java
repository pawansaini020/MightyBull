package com.pawan.MightyBull.entity;

import com.pawan.MightyBull.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Pawan Saini
 * Created on 11/01/25.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_score_info")
@Builder
public class StockScoreEntity extends BaseEntity<Long> {

    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "score")
    private Double score;

    @Column(name = "market_cap_score")
    private Double marketCapScore;

    @Column(name = "price_score")
    private Double priceScore;

    @Column(name = "pe_score")
    private Double peScore;

    @Column(name = "dividend_yield_score")
    private Double dividendYieldScore;

    @Column(name = "roce_score")
    private Double roceScore;

    @Column(name = "roc_score")
    private Double rocScore;

    @Column(name = "quarterly_profit_score")
    private Double quarterlyProfitScore;

    @Column(name = "profit_snd_loss_score")
    private Double profitAndLossScore;

    @Column(name = "balance_sheet_score")
    private Double balanceSheetScore;

    @Column(name = "cash_flow_score")
    private Double cashFlowScore;

    @Column(name = "debtor_days_score")
    private Double debtorDaysScore;

    @Column(name = "yearly_roce_score")
    private Double yearlyRoceScore;

    @Column(name = "shareholding_pattern_score")
    private Double shareholdingPatternScore;
}
