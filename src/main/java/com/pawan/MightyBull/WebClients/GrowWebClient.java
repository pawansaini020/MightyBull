package com.pawan.MightyBull.WebClients;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.grow.GrowMutualFundDetails;
import com.pawan.MightyBull.dto.grow.GrowStocks;
import com.pawan.MightyBull.dto.grow.request.GrowIndexDetails;
import com.pawan.MightyBull.dto.grow.request.GrowIndexRequest;
import com.pawan.MightyBull.dto.grow.request.GrowStockRequest;
import com.pawan.MightyBull.dto.grow.response.GrowIndexResponse;
import com.pawan.MightyBull.dto.grow.response.GrowMutualFundResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Slf4j
@Component
public class GrowWebClient extends Client {

    @Value("${grow.service.url}")
    private String serverUrl;

    private final WebClient webClient;

    @Autowired
    public GrowWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public GrowStocks getAllStockDetails(GrowStockRequest request) {
        try {
            String endPoint = ApiEndpointConstant.Grow.BASE + ApiEndpointConstant.Grow.STOCK_DATA + ApiEndpointConstant.Grow.ALL_STOCKS;
            HttpHeaders headers = getHeader(null, null);
            Mono<GrowStocks> response = webClient.post()
                    .uri(serverUrl + endPoint)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GrowStocks.class);
            GrowStocks growStocks = response.block();
            return growStocks != null ? growStocks : new GrowStocks();
        } catch (Exception e) {
            log.error("GROW_WEB_CLIENT ::: Error occurred while getting all stock details from source grow", e);
        }
        return new GrowStocks();
    }

    public GrowIndexResponse getIndianIndexDetails(GrowIndexRequest request) {
        try {
            String endPoint = ApiEndpointConstant.Grow.BASE + ApiEndpointConstant.Grow.STOCK_DATA + ApiEndpointConstant.Grow.LATEST_INDEX;
            HttpHeaders headers = getHeader(null, null);
            Mono<GrowIndexResponse> response = webClient.post()
                    .uri(serverUrl + endPoint)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GrowIndexResponse.class);
            GrowIndexResponse growStocks = response.block();
            return growStocks != null ? growStocks : new GrowIndexResponse();
        } catch (Exception e) {
            log.error("GROW_WEB_CLIENT ::: Error occurred while getting all index from source grow", e);
        }
        return new GrowIndexResponse();
    }

    public GrowIndexResponse getGlobalIndexDetails() {
        try {
            String endPoint = ApiEndpointConstant.Grow.BASE + ApiEndpointConstant.Grow.STOCK_DATA + ApiEndpointConstant.Grow.GLOBAL_INSTRUMENTS;
            URI uri = UriComponentsBuilder.fromUriString(endPoint)
                    .queryParam("instrumentType", "GLOBAL_INSTRUMENTS")
                    .build().toUri();
            HttpHeaders headers = getHeader(null, null);
            Mono<GrowIndexResponse> response = webClient.get()
                    .uri(serverUrl + uri.toString())
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(GrowIndexResponse.class);
            GrowIndexResponse growStocks = response.block();
            return growStocks != null ? growStocks : new GrowIndexResponse();
        } catch (Exception e) {
            log.error("GROW_WEB_CLIENT ::: Error occurred while getting all index from source grow", e);
        }
        return new GrowIndexResponse();
    }

    public GrowIndexDetails getIndexDetails(String indexId) {
        try {
            String endPoint = ApiEndpointConstant.Grow.BASE + ApiEndpointConstant.Grow.STOCK_DATA + ApiEndpointConstant.Grow.COMPANY_SEARCH + indexId;
            HttpHeaders headers = getHeader(null, null);
            Mono<GrowIndexDetails> response = webClient.get()
                    .uri(serverUrl + endPoint)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(GrowIndexDetails.class);
            GrowIndexDetails growStocks = response.block();
            return growStocks != null ? growStocks : new GrowIndexDetails();
        } catch (Exception e) {
            log.error("GROW_WEB_CLIENT ::: Error occurred while getting all index from source grow: {}", indexId, e);
        }
        return new GrowIndexDetails();
    }

    public GrowMutualFundResponse getAllMutualFundDetails(int pageNumber, int pageSize) {
        try {
            String endPoint = ApiEndpointConstant.Grow.BASE + ApiEndpointConstant.Grow.SEARCH_V1 + ApiEndpointConstant.Grow.DERIVED_SCHEME;
            URI uri = UriComponentsBuilder.fromUriString(endPoint)
                    .queryParam("available_for_investment", true)
                    .queryParam("doc_type", "scheme")
                    .queryParam("plan_type", "Direct")
                    .queryParam("sort_by", 3)
                    .queryParam("max_aum", "")
                    .queryParam("q", "")
                    .queryParam("page", pageNumber)
                    .queryParam("size", pageSize)
                    .build().toUri();
            HttpHeaders headers = getHeader(null, null);
            Mono<GrowMutualFundResponse> response = webClient.get()
                    .uri(serverUrl + uri.toString())
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(GrowMutualFundResponse.class);
            GrowMutualFundResponse growMutual = response.block();
            return growMutual != null ? growMutual : new GrowMutualFundResponse();
        } catch (Exception e) {
            log.error("GROW_WEB_CLIENT ::: Error occurred while getting all mutual fund from source grow: {}, {}", pageNumber, pageSize, e);
        }
        return new GrowMutualFundResponse();
    }

    public GrowMutualFundDetails getMutualFundDetails(String mutualFundId) {
        try {
            String endPoint = ApiEndpointConstant.Grow.BASE + ApiEndpointConstant.Grow.MUTUAL_FUND_DETAILS + mutualFundId;
            HttpHeaders headers = getHeader(null, null);
            Mono<GrowMutualFundDetails> response = webClient.get()
                    .uri(serverUrl + endPoint)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(GrowMutualFundDetails.class);
            GrowMutualFundDetails growMutual = response.block();
            return growMutual != null ? growMutual : new GrowMutualFundDetails();
        } catch (Exception e) {
            log.error("GROW_WEB_CLIENT ::: Error occurred while getting mutual fund details from grow for mutualFundId: {}", mutualFundId, e);
        }
        return new GrowMutualFundDetails();
    }
}
