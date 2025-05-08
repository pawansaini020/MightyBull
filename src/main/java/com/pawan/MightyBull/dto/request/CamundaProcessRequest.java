package com.pawan.MightyBull.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 09/01/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamundaProcessRequest {

    public Map<String, Map<String, Object>> variables;
    public String businessKey;
}
