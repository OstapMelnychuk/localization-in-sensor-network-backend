package com.best.friends.bachelor.random;

import com.best.friends.bachelor.node.Node;

import static java.lang.Math.*;

import java.util.Random;

public class DistributedRandomNumbers {
    private double r;

    public DistributedRandomNumbers(Node mainNode) {
        r = mainNode.getWifiRange();
    }

    public double generateRandomDistributedValue() {
        Random random = new Random();
        double x = random.nextDouble();
        return (-r) + (2 * r * x);
    }

    public double generateY(double x) {
        Random random = new Random();
        double rand = random.nextDouble();
        return -sqrt(pow(r, 2) - pow(x, 2)) + 2 * sqrt(pow(r, 2) - pow(x, 2)) * rand;
    }
}
