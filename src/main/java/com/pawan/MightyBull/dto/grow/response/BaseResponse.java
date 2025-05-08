package com.pawan.MightyBull.dto.grow.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pawan.MightyBull.constants.AppConstant;
import lombok.Data;
import org.apache.logging.log4j.ThreadContext;

import java.io.Serializable;
import java.util.List;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> implements Serializable {

    @JsonProperty("status")
    protected Boolean status;

    @JsonProperty("data")
    protected T data;

    @JsonProperty("pagination")
    protected PaginationResponse pagination;

    @JsonProperty("messages")
    protected List<String> messages;

    @JsonProperty("errors")
    protected List<ErrorResponse> errors;

    @JsonProperty("request_id")
    protected String requestId = ThreadContext.get(AppConstant.RequestHeader.REQUEST_ID);
}
