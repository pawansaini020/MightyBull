package com.pawan.MightyBull.WebClients;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.request.CamundaProcessRequest;
import com.pawan.MightyBull.dto.response.CamundaProcessResponse;
import com.pawan.MightyBull.dto.score.StockScoreInfoDTO;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 22/11/23.
 */
@Component
@Slf4j
public class CamundaWebClient extends Client {

    @Value("${camunda.engine.url}")
    private String baseUrl;

    @Value("${camunda.engine.process.id}")
    private String processId;

    private final WebClient webClient;

    @Autowired
    public CamundaWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public CamundaProcessResponse calculateScore(StockScoreInfoDTO scoreInfoDTO) {
        try {
            CamundaProcessRequest camundaProcessRequest = getRequest(scoreInfoDTO);

            String endPoint = ApiEndpointConstant.Camunda.BASE + ApiEndpointConstant.Camunda.PROCESS_DEFINITION
                    + processId + ApiEndpointConstant.Camunda.START;
            Mono<CamundaProcessResponse> response = webClient.post()
                    .uri(baseUrl + endPoint)
                    .bodyValue(camundaProcessRequest)
                    .retrieve()
                    .bodyToMono(CamundaProcessResponse.class);
            return response.block();
        } catch (Exception e) {
            log.error("CAMUNDA_WEB_CLIENT ::: Error occurred in cab score request for stock_id: {}", scoreInfoDTO.getScoreInfo().getStockId(), e);
            throw e;
        }
    }

    private CamundaProcessRequest getRequest(StockScoreInfoDTO scoreInfoDTO) {
        Map<String, Map<String, Object>> request = new HashMap<>();
        Map<String, Object> stockId = new HashMap<>();
        stockId.put("type", "String");
        stockId.put("value", scoreInfoDTO.getScoreInfo().getStockId());
        request.put("stock_id", stockId);

        Map<String, Object> data = new HashMap<>();
        data.put("type", "String");
        data.put("value",  GsonUtils.getGson().toJson(scoreInfoDTO.getData()));
        request.put("data", data);

        Map<String, Object> scoreInfo = new HashMap<>();
        scoreInfo.put("type", "String");
        scoreInfo.put("value", GsonUtils.getGson().toJson(scoreInfoDTO.getScoreInfo()));
        request.put("scoreInfo", scoreInfo);

        Map<String, Object> rules = new HashMap<>();
        rules.put("type", "String");
        rules.put("value", GsonUtils.getGson().toJson(scoreInfoDTO.getRules()));
        request.put("rules", rules);

        return new CamundaProcessRequest(request, scoreInfoDTO.getScoreInfo().getStockId());
    }
}
