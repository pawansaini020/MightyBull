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
public class GrowStockDetails extends BaseDto implements Serializable {

    private String stockId;
    private String isin;
    private String growwContractId;
    private String companyName;
    private String companyShortName;
    private String searchId;
    private Integer industryCode;
    private Long bseScriptCode;
    private String nseScriptCode;
    private Double yearlyHighPrice;
    private Double yearlyLowPrice;
    private Double closePrice;
    private Long marketCap;
    private String logoUrl;
    private GrowLivePriceDto livePriceDto;
}
