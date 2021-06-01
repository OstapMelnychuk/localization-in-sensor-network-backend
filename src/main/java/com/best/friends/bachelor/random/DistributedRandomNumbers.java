package com.best.friends.bachelor.random;

import com.best.friends.bachelor.node.Node;

import java.util.Random;

public class DistributedRandomNumbers {
    private double r;

    public DistributedRandomNumbers(Node mainNode) {
        r = mainNode.getWifiRange();
    }

    public double generateRandomDistributedValue() {
        Random random = new Random();
        double x = random.nextDouble();
        return (-r) + (2*r * x);
    }
}
