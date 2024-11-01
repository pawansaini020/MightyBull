package com.pawan.MightyBull.WebClients;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.grow.GrowStocks;
import com.pawan.MightyBull.dto.grow.request.GrowStockRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
}
