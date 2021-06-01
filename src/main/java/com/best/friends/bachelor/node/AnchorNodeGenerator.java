package com.best.friends.bachelor.node;

import com.best.friends.bachelor.random.DistributedRandomNumbers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnchorNodeGenerator {
    private final DistributedRandomNumbers randomNumbers;
    private static final double WIFI_RADIUS = 300;

    public AnchorNodeGenerator(Node mainNode) {
        randomNumbers = new DistributedRandomNumbers(mainNode);
    }

    public List<Node> generateAnchorNodes(int quantity) {
        CopyOnWriteArrayList<Node> anchorNodes = new CopyOnWriteArrayList<Node>();
        for (int i = 0; i < quantity ; i++) {
            anchorNodes.add(new Node(randomNumbers.generateRandomDistributedValue(),
                    randomNumbers.generateRandomDistributedValue(), WIFI_RADIUS));
        }

        return anchorNodes;
    }

    public Node generateRandomNode() {
        return new Node(randomNumbers.generateRandomDistributedValue(),
                randomNumbers.generateRandomDistributedValue(), WIFI_RADIUS);
    }
}
