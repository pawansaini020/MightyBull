package com.pawan.MightyBull.utils;

import com.pawan.MightyBull.dto.ScoreRule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 07/11/24.
 */
@Slf4j
public class ScoreUtils {

    public static int calculateScore(Double value, List<ScoreRule> rules) {
        try {
            if(value != null) {
                for (ScoreRule rule : rules) {
                    if (rule.getFrom() <= value && value <= rule.getTo()) {
                        return (int) (rule.getScore() * rule.getWeight());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while calculating score for value: {}, rules: {}", value, GsonUtils.getGson().toJson(rules));
        }
        return 0;
    }
}
