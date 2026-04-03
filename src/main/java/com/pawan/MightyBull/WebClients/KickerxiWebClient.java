package com.pawan.MightyBull.WebClients;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class KickerxiWebClient extends Client {

    @Value("${kickerxi.service.url}")
    private String serverUrl;

    @Value("${kickerxi.service.url1}")
    private String serverUrl1;

    private final WebClient webClient;

    @Autowired
    public KickerxiWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String awakeKickerxiServer() {
        try {
            String endPoint = ApiEndpointConstant.Kickerxi.PING;
            HttpHeaders headers = getHeader(null, null);
            Mono<String> response = webClient.get()
                    .uri(serverUrl + endPoint)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(String.class);
            return response.block();
        } catch (Exception e) {
            log.error("MIGHTYBULL_WEB_CLIENT ::: Error occurred while awaking kickerxi server", e);
        }
        return null;
    }

    public String awakeKickerxiServer1() {
        try {
            String endPoint = ApiEndpointConstant.Kickerxi.PING;
            HttpHeaders headers = getHeader(null, null);
            Mono<String> response = webClient.get()
                    .uri(serverUrl1 + endPoint)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(String.class);
            return response.block();
        } catch (Exception e) {
            log.error("MIGHTYBULL_WEB_CLIENT ::: Error occurred while awaking kickerxi server", e);
        }
        return null;
    }
}
