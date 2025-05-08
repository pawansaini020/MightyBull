package com.pawan.MightyBull.dto.grow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pawan.MightyBull.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowLivePriceDto extends BaseDto implements Serializable {

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
