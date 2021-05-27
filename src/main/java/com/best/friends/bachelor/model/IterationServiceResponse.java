package com.best.friends.bachelor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class IterationServiceResponse {
    private double min;
    private double max;
    private double average;
    private Map<Integer, Double> iterationPrecision;
}
