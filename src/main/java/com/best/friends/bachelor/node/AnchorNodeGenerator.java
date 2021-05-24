package com.best.friends.bachelor.node;

import com.best.friends.bachelor.random.NormalRandomNumbers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnchorNodeGenerator {
    private final NormalRandomNumbers randomNumbers;
    private static final double MEAN = 200;
    private static final double VARIANCE = 200;
    private static final double WIFI_RADIUS = 300;

    public AnchorNodeGenerator(Node mainNode) {
        randomNumbers = new NormalRandomNumbers(mainNode, (int) VARIANCE);
    }

    public List<Node> generateAnchorNodes(int quantity) {
        CopyOnWriteArrayList<Node> anchorNodes = new CopyOnWriteArrayList<Node>();
        for (int i = 0; i < quantity ; i++) {
            anchorNodes.add(new Node(randomNumbers.generateRandomNormalValue(),
                    randomNumbers.generateRandomNormalValue(), WIFI_RADIUS));
        }

        return anchorNodes;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
