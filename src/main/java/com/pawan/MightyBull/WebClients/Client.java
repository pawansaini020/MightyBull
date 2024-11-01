package com.pawan.MightyBull.WebClients;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Slf4j
@Component
public class Client {

    public HttpHeaders getHeader(String tokenKey, String tokenValue) {
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        if(StringUtils.isNotBlank(tokenKey) && StringUtils.isNotBlank(tokenValue)) {
            header.add(tokenKey, tokenValue);
        }
        return header;
    }

    public StringBuilder buildUrl(Object... args) {
        StringBuilder query = new StringBuilder();
        try {
            for (Object arg : args) {
                if(!query.isEmpty()) {
                    query.append("/");
                }
                query.append(arg);
            }
            return query;
        } catch (Exception e) {
            log.error("Error occurred while building query with args: {}", args, e);
        }
        return query;
    }

    public StringBuilder buildParams(List<String> args) {
        StringBuilder query = new StringBuilder();
        try {
            for (Object arg : args) {
                if(!query.isEmpty()) {
                    query.append(",");
                }
                query.append(arg);
            }
            return query;
        } catch (Exception e) {
            log.error("Error occurred while building params with args: {}", args);
        }
        return query;
    }
}
