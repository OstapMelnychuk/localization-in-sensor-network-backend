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
        CopyOnWriteArrayList<Node> anchorNodes = new CopyOnWriteArrayList<>();
        for (int i = 0; i < quantity ; i++) {
            double x = randomNumbers.generateRandomDistributedValue();
            anchorNodes.add(new Node(x,
                    randomNumbers.generateY(x), WIFI_RADIUS));
        }
        return anchorNodes;
    }

    public Node generateRandomNode() {
        double x = randomNumbers.generateRandomDistributedValue();
        return new Node(x,
                randomNumbers.generateY(x), WIFI_RADIUS);
    }

    public void checkAllNodesBelongToMainNode(Node mainNode, List<Node> anchorNodes, int startPosition, double calculationError) {
        for (int i = startPosition; i < anchorNodes.size(); i++) {
            Node node = anchorNodes.get(i);
            node.setWifiRange(CountingUtils.countDistanceBetweenNodes(mainNode, node) * calculationError);
            boolean isNotInTheRangeOfMainNode = !CountingUtils.checkIfPointIsInTheCircle(mainNode, node);
            boolean isIntersectingWithOtherAnchorNodes = checkAllAnchorNodesIntersectWithEachOther(node, anchorNodes, i);
            boolean isNotCenterOfMainNodeInTheCircle = !CountingUtils.checkIfPointIsInTheCircle(node, mainNode);
            while ((isNotInTheRangeOfMainNode || !isIntersectingWithOtherAnchorNodes || isNotCenterOfMainNodeInTheCircle)) {
                node = generateRandomNode();
                anchorNodes.set(i, node);
                node.setWifiRange(CountingUtils.countDistanceBetweenNodes(mainNode, node) * calculationError);
                isIntersectingWithOtherAnchorNodes = checkAllAnchorNodesIntersectWithEachOther(node, anchorNodes, i);
//                   isNotInTheRangeOfMainNode = !CountingUtils.checkIfPointIsInTheCircle(mainNode, node);
                isNotCenterOfMainNodeInTheCircle = !CountingUtils.checkIfPointIsInTheCircle(node, mainNode);

            }
        }
        anchorNodes.forEach(n -> n.setWifiRange(CountingUtils.countDistanceBetweenNodes(mainNode, n) * calculationError));
    }

    private boolean checkAllAnchorNodesIntersectWithEachOther(Node nodeToCheck, List<Node> anchorNodes, int iteration) {
        for (int i = 0; i < iteration; i++) {
            if (!CountingUtils.checkIfCirclesIntersect(nodeToCheck, anchorNodes.get(i))) {
                return false;
            }
        }
        return true;
    }
}
