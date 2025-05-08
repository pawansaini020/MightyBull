package com.pawan.MightyBull.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 09/01/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CamundaProcessResponse {

    private String id;
    private String definitionId;
    private String businessKey;
    private String caseInstanceId;
    private Boolean ended;
    private Boolean suspended;
    private String tenantId;
    private List<Object> links;
}
