package com.pawan.MightyBull.dto.grow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowStocks implements Serializable {

    private List<GrowStockDetails> records;
    private Integer totalRecords;
}
