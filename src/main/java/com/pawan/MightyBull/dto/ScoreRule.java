package com.pawan.MightyBull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pawan Saini
 * Created on 07/11/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRule {

    private double from;
    private double to;
    private double score;
    private double weight;
}
