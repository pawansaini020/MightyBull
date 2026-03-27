package com.pawan.MightyBull.entity.mongo;

import com.pawan.MightyBull.entity.base.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "mutual_funds")
public class MutualFundDocument extends BaseDocument<Long> {

    @Indexed
    private String mutualFundId;

    private String name;

    private String fundHouse;

    private String fundManager;

    private String amc;

    private String planType;

    private String category;

    private String subCategory;

    private List<String> subSubCategory;

    private String risk;

    private Double riskRating;

    private Boolean index;

    private String logoUrl;

    private Double return1d;

    private Double return1y;

    private Double return3y;

    private Double return5y;

    private Long minInvestmentAmount;

    private Long minSipInvestment;

    private Double aum;
}
