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
@Document(collection = "stock_details")
public class StockDetailsDocument extends BaseDocument<Long> {

    @Indexed
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
}
