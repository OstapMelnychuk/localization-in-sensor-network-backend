package com.best.friends.bachelor.node;

import com.best.friends.bachelor.random.NormalRandomNumbers;

public class RandomNodeFactory {
    private static final double MEAN = 200;
    private static final double VARIANCE = 200;
    private static final NormalRandomNumbers normalRandomNumbers =
            new NormalRandomNumbers(MEAN, VARIANCE);
    private static final int WIFI_RADIUS = 300;

    public static Node generateRandomNode() {
        return new Node(
                normalRandomNumbers.generateRandomNormalValue(),
                normalRandomNumbers.generateRandomNormalValue(),
                WIFI_RADIUS
        );
    }
}
