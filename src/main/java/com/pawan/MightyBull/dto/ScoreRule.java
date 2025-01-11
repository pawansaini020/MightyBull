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

    private int from;
    private int to;
    private int score;
    private double weight;
}
