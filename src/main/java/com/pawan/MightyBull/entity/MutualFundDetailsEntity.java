package com.pawan.MightyBull.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pawan.MightyBull.dto.mutualfund.MutualFundReturnStatsDto;
import com.pawan.MightyBull.dto.mutualfund.MutualFundStockHoldingDto;
import com.pawan.MightyBull.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mutual_fund_details")
public class MutualFundDetailsEntity extends BaseEntity<Long> {

    @Column(name = "search_id")
    private String mutualFundId;

    @Column(name = "benchmark_name")
    private String benchmarkName;

    @Column(name = "meta_desc")
    private String metaDesc;

    @Column(name = "rank")
    private Double rank;

    @Column(name = "nav")
    private Double nav;

    @Column(name = "nav_date")
    private String navDate;

    @Column(name = "launch_date")
    private String launchDate;

    @Column(name = "exit_load")
    private String exitLoadMessage;

    @Column(name = "expense_ratio")
    private Double expenseRatio;

    @Column(name = "stamp_duty")
    private String stampDuty;

    @Column(name = "dividend")
    private Double dividend;

    @Column(name = "analysis")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> analysis;

    @Column(name = "return_stats")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<MutualFundReturnStatsDto> returnStats;

    @Column(name = "holdings")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<MutualFundStockHoldingDto> holdings;

    @Column(name = "lock_in")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Double> lockIn;
}
