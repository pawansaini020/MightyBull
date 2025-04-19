package com.pawan.MightyBull.dto.grow.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pawan.MightyBull.dto.grow.ChildAssets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrowIndexDetails {

    private Map<String, Object> header;
    private List<ChildAssets> childAssets;
}
