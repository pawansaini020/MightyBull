package com.pawan.MightyBull.dto.score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Pawan Saini
 * Created on 09/01/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class StockScoreInfoDTO implements Serializable {

    private StockScoreData data;
    private ScoreScoreConfigDTO rules;
    private StockScoreDTO scoreInfo;
}
