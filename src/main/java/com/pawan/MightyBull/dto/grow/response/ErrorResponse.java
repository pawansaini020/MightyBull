package com.pawan.MightyBull.dto.grow.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Data
@Builder
public class ErrorResponse {

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_type")
    private String errorType;

    @JsonProperty("error_message")
    private String errorMessage;
}
