package com.pawan.MightyBull.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mutual_funds")
public class MutualFundEntity extends BaseEntity<Long> {

    @Column(name = "mutual_fund_id")
    private String mutualFundId;

    @Column(name = "name")
    private String name;

    @Column(name = "fund_house")
    private String fundHouse;

    @Column(name = "fund_manager")
    private String fundManager;

    @Column(name = "amc")
    private String amc;

    @Column(name = "plan_type")
    private String planType;

    @Column(name = "category")
    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sub_sub_category")
    private List<String> subSubCategory;

    @Column(name = "risk")
    private String risk;

    @Column(name = "risk_rating")
    private Double riskRating;

    @Column(name = "index")
    private Boolean index;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "return1d")
    private Double return1d;

    @Column(name = "return1y")
    private Double return1y;

    @Column(name = "return3y")
    private Double return3y;

    @Column(name = "return5y")
    private Double return5y;

    @Column(name = "min_investment_amount")
    private Long minInvestmentAmount;

    @Column(name = "min_sip_investment")
    private Long minSipInvestment;

    @Column(name = "aum")
    private Double aum;
}
