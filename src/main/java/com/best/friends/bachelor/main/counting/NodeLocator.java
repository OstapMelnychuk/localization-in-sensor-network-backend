package com.best.friends.bachelor.main.counting;

import com.best.friends.bachelor.node.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class NodeLocator {
    private Node mainNode;
    private List<Node> anchorNodes;
    private double calculationError = 1.1d;
    private List<Node> anchorNodesIntersectionPoints;
    private NodeLocatorResponse nodeLocatorResponse;
    private int iterationQuantity;
    private List<IterationNodeTableRow> iterationNodeTable;

    public void initiate(int anchorNodeQuantity, double calculationError, int iterationQuantity) {
        this.calculationError = calculationError;
        this.iterationQuantity = iterationQuantity;
        mainNode = RandomNodeFactory.generateRandomNode();
        AnchorNodeGenerator anchorNodeGenerator = new AnchorNodeGenerator(mainNode);
        System.out.println("Main Node");
        System.out.println(mainNode);
        anchorNodes = anchorNodeGenerator.generateAnchorNodes(anchorNodeQuantity);
        checkAllNodesBelongToMainNode(mainNode, anchorNodes, 0);
        System.out.println("Anchor Nodes:");
        anchorNodes.forEach(System.out::println);
        anchorNodesIntersectionPoints = new CopyOnWriteArrayList<>();
        nodeLocatorResponse = new NodeLocatorResponse();
        iterationNodeTable = new ArrayList<>();
    }

    public NodeLocatorResponse start() {
        findAnchorNodeIntersectionPointsMoreThanFour(0);
        filterPoints();
        Node mainNodeLoc = locateMainNode();
        System.out.println("Main node loc:");
        System.out.println(mainNodeLoc);
        return iterative(mainNodeLoc);
    }

    public NodeLocatorResponse addNode(NodeLocatorResponse initData, double calculationError, int iterationQuantity) {
        mainNode = initData.getMainNode();
        anchorNodes = initData.getAnchorNodes();
        anchorNodes.add(RandomNodeFactory.generateRandomNode());
        anchorNodesIntersectionPoints = new CopyOnWriteArrayList<>();
        iterationNodeTable = new ArrayList<>();
        this.iterationQuantity = iterationQuantity;
        this.calculationError = calculationError;
        nodeLocatorResponse = new NodeLocatorResponse();
        checkAllNodesBelongToMainNode(mainNode, anchorNodes, anchorNodes.size() - 1);
        findAnchorNodeIntersectionPointsMoreThanFour(0);
        filterPoints();
        Node mainNodeLoc = locateMainNode();
        System.out.println("Main node loc:");
        System.out.println(mainNodeLoc);
        return iterative(mainNodeLoc);
    }

    private void findAnchorNodeIntersectionPointsMoreThanFour(int startPosition) {
        for (int i = startPosition; i < anchorNodes.size(); i++) {
            for (int j = i + 1; j < anchorNodes.size(); j++) {
                Node firstNode = anchorNodes.get(i);
                Node secondNode = anchorNodes.get(j);
                double distance = CountingUtils.countDistanceBetweenNodes(firstNode, secondNode);
                if (CountingUtils.checkIfCirclesIntersect(firstNode, secondNode)) {
                    anchorNodesIntersectionPoints.addAll(countIntersectionPoint(firstNode, secondNode, distance));
                }
            }
        }
        System.out.println("Result Nodes:");
        anchorNodesIntersectionPoints = anchorNodesIntersectionPoints.stream().distinct().collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        anchorNodesIntersectionPoints.forEach(System.out::println);
    }

    private double countDistanceToHeightPoint(Node mainNode, Node anchorNode, double distance) {
        double p0 = mainNode.getWifiRange();
        double p1 = anchorNode.getWifiRange();
        return (Math.pow(p0, 2) - Math.pow(p1, 2) + Math.pow(distance, 2)) / (2 * distance);
    }

    private Node countHeightPointCoordinates(Node mainNode, Node anchorNode, double distance) {
        double a = countDistanceToHeightPoint(mainNode, anchorNode, distance);
        return mainNode.addNode(
                anchorNode.subtractNode(mainNode)
                        .multiplyNodeAndReturnNew(a / distance)
        );
    }

    private double countHeight(Node mainNode, Node anchorNode, double distance) {
        double a = countDistanceToHeightPoint(mainNode, anchorNode, distance);
        return Math.sqrt(Math.pow(mainNode.getWifiRange(), 2) - Math.pow(a, 2));
    }

    private List<Node> countIntersectionPoint(Node mainNode, Node anchorNode, double distance) {
        double h = countHeight(mainNode, anchorNode, distance);
        Node heightPoint = countHeightPointCoordinates(mainNode, anchorNode, distance);
        List<Node> resultNodes = new ArrayList<>();
        resultNodes.add(new Node(
                heightPoint.getX() + ((h / distance) * (anchorNode.getY() - mainNode.getY())),
                heightPoint.getY() - ((h / distance) * (anchorNode.getX() - mainNode.getX())),
                3
        ));
        resultNodes.add(new Node(
                heightPoint.getX() - ((h / distance) * (anchorNode.getY() - mainNode.getY())),
                heightPoint.getY() + ((h / distance) * (anchorNode.getX() - mainNode.getX())),
                3
        ));
        return resultNodes;
    }

    private void filterPoints() {
        for (Node point : anchorNodesIntersectionPoints) {
            boolean isChecked;
            for (Node anchorNode : anchorNodes) {
                isChecked = CountingUtils.checkIfPointIsInTheCircle(anchorNode, point);
                if (!isChecked) {
                    anchorNodesIntersectionPoints.remove(point);
                    break;
                }
            }
        }
        System.out.println("Filtered Points:");
        anchorNodesIntersectionPoints.forEach(System.out::println);
        nodeLocatorResponse.setFilteredPoints(new ArrayList<>(anchorNodesIntersectionPoints));
    }

    private void checkAllNodesBelongToMainNode(Node mainNode, List<Node> anchorNodes, int startPosition) {
        for (int i = startPosition; i < anchorNodes.size(); i++) {
            Node node = anchorNodes.get(i);
            node.setWifiRange(CountingUtils.countDistanceBetweenNodes(mainNode, node) * calculationError);
            boolean isNotInTheRangeOfMainNode = !CountingUtils.checkIfPointIsInTheCircle(mainNode, node);
            boolean isIntersectingWithOtherAnchorNodes = checkAllAnchorNodesIntersectWithEachOther(node, i);
            boolean isNotCenterOfMainNodeInTheCircle = !CountingUtils.checkIfPointIsInTheCircle(node, mainNode);
            while ((isNotInTheRangeOfMainNode || !isIntersectingWithOtherAnchorNodes || isNotCenterOfMainNodeInTheCircle)) {
                node = RandomNodeFactory.generateRandomNode();
                anchorNodes.set(i, node);
                node.setWifiRange(CountingUtils.countDistanceBetweenNodes(mainNode, node) * calculationError);
                isNotInTheRangeOfMainNode = !CountingUtils.checkIfPointIsInTheCircle(mainNode, node);
                isIntersectingWithOtherAnchorNodes = checkAllAnchorNodesIntersectWithEachOther(node, i);
                isNotCenterOfMainNodeInTheCircle = !CountingUtils.checkIfPointIsInTheCircle(node, mainNode);

            }
        }
    }

    private boolean checkAllAnchorNodesIntersectWithEachOther(Node nodeToCheck, int iteration) {
        for (int i = 0; i < iteration; i++) {
            if (!CountingUtils.checkIfCirclesIntersect(nodeToCheck, anchorNodes.get(i))) {
                return false;
            }
        }
        return true;
    }

    private NodeLocatorResponse iterative(Node mainNodeLoc) {
        System.out.println("After Iterations");
        for (int i = 0; i < iterationQuantity; i++) {
            anchorNodesIntersectionPoints.remove(countBiggestDistanceToLocatedPoint(mainNodeLoc));
            anchorNodesIntersectionPoints.add(mainNodeLoc);
            mainNodeLoc = locateMainNode();
            iterationNodeTable.add(new IterationNodeTableRow(mainNodeLoc,
                    CountingUtils.countDistanceBetweenNodes(mainNode, mainNodeLoc)));
            System.out.println(mainNodeLoc);
        }
        System.out.println("Iterations table:");
        iterationNodeTable.forEach(System.out::println);
        nodeLocatorResponse.setMainNode(mainNode);
        nodeLocatorResponse.setAnchorNodes(anchorNodes);
        nodeLocatorResponse.setPoints(anchorNodesIntersectionPoints);
        nodeLocatorResponse.setMainNodeLoc(mainNodeLoc);
        nodeLocatorResponse.setIterationTable(iterationNodeTable);
        nodeLocatorResponse.saveAllFieldsInFiles();
        return nodeLocatorResponse;
    }

    private Node countBiggestDistanceToLocatedPoint(Node mainNodeLoc) {
        List<Double> distances = new ArrayList<>();
        for (Node point : anchorNodesIntersectionPoints) {
            distances.add(CountingUtils.countDistanceBetweenNodes(mainNodeLoc, point));
        }
        double biggestDistance = 0;
        Node node = null;

        for (int i = 0; i < distances.size(); i++) {
            if (biggestDistance < distances.get(i)) {
                biggestDistance = distances.get(i);
                node = anchorNodesIntersectionPoints.get(i);
            }
        }
        return node;
    }


    private Node locateMainNode() {
        double x = 0;
        double y = 0;
        for (Node point : anchorNodesIntersectionPoints) {
            x += point.getX();
            y += point.getY();
        }
        x = x / anchorNodesIntersectionPoints.size();
        y = y / anchorNodesIntersectionPoints.size();

        return new Node(x, y, 100);
    }
}
