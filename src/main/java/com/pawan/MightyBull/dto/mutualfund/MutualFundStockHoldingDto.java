package com.pawan.MightyBull.dto.mutualfund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MutualFundStockHoldingDto {

    @JsonProperty(value = "stock_id")
    private String stockId;

    @JsonProperty(value = "stock_search_id")
    private String stockSearchId;

    @JsonProperty(value = "portfolio_date")
    private Date portfolioDate;

    @JsonProperty(value = "company_name")
    private String companyName;

    @JsonProperty(value = "sector_name")
    private String sectorName;

    @JsonProperty(value = "instrument_name")
    private String instrumentName;

    @JsonProperty(value = "market_value")
    private Double marketValue;

    @JsonProperty(value = "corpus_per")
    private Double corpusPer;
}
