package com.pawan.MightyBull.dto.grow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildAssets {

    private GrowStockDetails header;
    private Double mktCap;
}
