package com.pawan.MightyBull.dto;

import com.pawan.MightyBull.enums.FilterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterCondition {

    private String field;
    private FilterType type;
    private Object value;
    private Object secondValue;
}
