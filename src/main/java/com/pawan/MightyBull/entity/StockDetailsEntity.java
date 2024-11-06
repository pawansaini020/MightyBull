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
 * Created on 01/11/24.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_details")
@Builder
public class StockDetailsEntity extends BaseEntity<Long> {

    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "isin")
    private String isin;

    @Column(name = "groww_contract_id")
    private String growwContractId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_short_name")
    private String companyShortName;

    @Column(name = "search_id")
    private String searchId;

    @Column(name = "industry_code")
    private Integer industryCode;

    @Column(name = "bse_script_code")
    private Long bseScriptCode;

    @Column(name = "nse_script_code")
    private String nseScriptCode;

    @Column(name = "yearly_high_price")
    private Double yearlyHighPrice;

    @Column(name = "yearly_low_price")
    private Double yearlyLowPrice;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "market_cap")
    private Long marketCap;
}
