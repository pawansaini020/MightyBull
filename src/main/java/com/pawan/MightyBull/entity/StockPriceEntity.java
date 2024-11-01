package com.pawan.MightyBull.entity;

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
@Table(name = "stock_price")
@Builder
public class StockPriceEntity extends BaseEntity<Long> {

    @Column(name = "type")
    private String type;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "dump_time")
    private Long tsInMillis;

    @Column(name = "open")
    private Double open;

    @Column(name = "high")
    private Double high;

    @Column(name = "low")
    private Double low;

    @Column(name = "close")
    private Double close;

    @Column(name = "ltp")
    private Double ltp;

    @Column(name = "day_change")
    private Double dayChange;

    @Column(name = "day_change_perc")
    private Double dayChangePerc;

    @Column(name = "low_price_range")
    private Double lowPriceRange;

    @Column(name = "high_price_range")
    private Double highPriceRange;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "total_buy_qty")
    private Long totalBuyQty;

    @Column(name = "total_sell_qty")
    private Long totalSellQty;

    @Column(name = "oi_day_change")
    private Long oiDayChange;

    @Column(name = "oi_day_change_perc")
    private Long oiDayChangePerc;

    @Column(name = "last_trade_qty")
    private Long lastTradeQty;

    @Column(name = "last_trade_time")
    private Long lastTradeTime;
}
