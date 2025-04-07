package com.pawan.MightyBull.entity;

import com.pawan.MightyBull.dto.Screener.ScreenerStockDetails;
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

import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "screener_stock_details")
public class ScreenerStockDetailsEntity extends BaseEntity<Long> {

    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "name")
    private String name;

    @Column(name = "bse_code")
    private Long bseCode;

    @Column(name = "nse_code")
    private String nseCode;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "sector")
    private String sector;

    @Column(name = "industry")
    private String industry;

    @Column(name = "market_cap")
    private Double marketCap;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "high")
    private Double high;

    @Column(name = "low")
    private Double low;

    @Column(name = "stock_pe")
    private Double stockPE;

    @Column(name = "book_value")
    private Double bookValue;

    @Column(name = "dividend_yield")
    private Double dividendYield;

    @Column(name = "roce")
    private Double roce;

    @Column(name = "roe")
    private Double roe;

    @Column(name = "face_value")
    private Double faceValue;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "peer_comparison", columnDefinition = "jsonb")
    private Map<String, Map<String, Double>> peerComparison;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "quarterly_results", columnDefinition = "jsonb")
    private Map<String, Map<String, Double>> quarterlyResults;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "profit_and_loss", columnDefinition = "jsonb")
    private Map<String, Map<String, Double>> profitAndLoss;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "balance_sheet", columnDefinition = "jsonb")
    private Map<String, Map<String, Double>> balanceSheet;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "cash_flows", columnDefinition = "jsonb")
    private Map<String, Map<String, Double>> cashFlows;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ratios", columnDefinition = "jsonb")
    private Map<String, Map<String, Double>> ratios;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "shareholding_pattern", columnDefinition = "jsonb")
    private Map<String, Map<String, Double>> shareholdingPattern;

    @Column(name = "score")
    private Double score;

    public void setRequiredDetails(ScreenerStockDetails stockDetails) {
        this.setCompanyId(stockDetails.getCompanyId());
        this.setWarehouseId(stockDetails.getWarehouseId());
        this.setSector(stockDetails.getSector());
        this.setIndustry(stockDetails.getIndustry());
        this.setMarketCap(stockDetails.getMarketCap());
        this.setCurrentPrice(stockDetails.getCurrentPrice());
        this.setHigh(stockDetails.getHigh());
        this.setLow(stockDetails.getLow());
        this.setStockPE(stockDetails.getStockPE());
        this.setBookValue(stockDetails.getBookValue());
        this.setDividendYield(stockDetails.getDividendYield());
        this.setRoce(stockDetails.getRoce());
        this.setRoe(stockDetails.getRoe());
        this.setFaceValue(stockDetails.getFaceValue());
        this.setPeerComparison(stockDetails.getPeerComparison());
        this.setQuarterlyResults(stockDetails.getQuarterlyResults());
        this.setProfitAndLoss(stockDetails.getProfitAndLoss());
        this.setBalanceSheet(stockDetails.getBalanceSheet());
        this.setCashFlows(stockDetails.getCashFlows());
        this.setShareholdingPattern(stockDetails.getShareholdingPattern());
        this.setScore(stockDetails.getScore());
    }
}
