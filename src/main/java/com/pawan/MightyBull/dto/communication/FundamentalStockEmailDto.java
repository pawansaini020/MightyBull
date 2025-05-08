package com.pawan.MightyBull.dto.communication;

import com.pawan.MightyBull.dto.BaseDto;
import com.pawan.MightyBull.dto.Screener.ScreenerStockDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 19/01/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundamentalStockEmailDto extends BaseDto {

    private List<ScreenerStockDetails> stocks;
}
