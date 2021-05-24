package com.best.friends.bachelor.random;

import cern.jet.random.Normal;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import com.best.friends.bachelor.node.Node;

public class NormalRandomNumbers {
    private final RandomEngine randomEngine;
    private final Normal normal;

    public NormalRandomNumbers(double variance, double mean) {
        randomEngine = new DRand();
        normal = new Normal(variance, mean, randomEngine);
    }

    public NormalRandomNumbers(Node mainNode, int wifiRadius) {
        randomEngine = new DRand();
        normal = new Normal(wifiRadius, (mainNode.getX() + mainNode.getY()) / 2, randomEngine);
    }

    public double generateRandomNormalValue() {
        return normal.nextDouble();
    }
}
