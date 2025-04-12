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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "indexes")
public class IndexEntity extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "country")
    private String country;

    @Column(name = "value")
    private Double value;

    @Column(name = "open")
    private Double open;

    @Column(name = "close")
    private Double close;

    @Column(name = "day_change")
    private Double dayChange;

    @Column(name = "day_change_perc")
    private Double dayChangePerc;

    @Column(name = "low")
    private Double low;

    @Column(name = "high")
    private Double high;

    @Column(name = "year_low_price")
    private Double yearLowPrice;

    @Column(name = "year_high_price")
    private Double yearHighPrice;

    @Column(name = "logo_url")
    private String logoUrl;
}
