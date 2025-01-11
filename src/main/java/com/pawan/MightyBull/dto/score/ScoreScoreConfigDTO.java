package com.pawan.MightyBull.dto.score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pawan Saini
 * Created on 09/01/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ScoreScoreConfigDTO {

    private boolean marketCapScoreEnable;
    private boolean priceScoreEnable;
    private boolean peScoreEnable;
    private boolean dividendYieldScoreEnable;
    private boolean roceScoreEnable;
    private boolean rocScoreEnable;
    private boolean quarterlyProfitScoreEnable;
    private boolean profitAndLossScoreEnable;
    private boolean balanceSheetScoreEnable;
    private boolean cashFlowScoreEnable;
    private boolean debtorDaysScoreEnable;
    private boolean yearlyRoceScoreEnable;
    private boolean shareholdingPatternScoreEnable;
}
