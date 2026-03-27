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
@Document(collection = "stock_price")
public class StockPriceDocument extends BaseDocument<Long> {

    @Indexed
    private String stockId;

    private String type;

    private String symbol;

    private Long tsInMillis;

    private Double open;

    private Double high;

    private Double low;

    private Double close;

    private Double ltp;

    private Double dayChange;

    private Double dayChangePerc;

    private Double lowPriceRange;

    private Double highPriceRange;

    private Long volume;

    private Long totalBuyQty;

    private Long totalSellQty;

    private Long oiDayChange;

    private Long oiDayChangePerc;

    private Long lastTradeQty;

    private Long lastTradeTime;
}
