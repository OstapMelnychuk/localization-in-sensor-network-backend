package com.best.friends.bachelor.node;

import static java.lang.Math.*;

public class CountingUtils {
    public static double countDistanceBetweenTwoPoints(double x, double y, double x1, double y1) {
        return sqrt(pow(x1 - x, 2) + pow(y1 - y, 2));
    }

    @SuppressWarnings("All")
    public static double countDistanceBetweenNodes(Node node, Node node1) {
        double x2 = pow(node1.getX() - node.getX(), 2);
        double y2 = pow(node1.getY() - node.getY(), 2);
        double result = sqrt(x2 + y2);
        return result;
    }

    @SuppressWarnings("All")
    public static boolean checkIfPointIsInTheCircle(Node mainNode, Node subNode) {
        double x2 = pow(subNode.getX() - mainNode.getX(), 2);
        double y2 = pow(subNode.getY() - mainNode.getY(), 2);
        double r2 = pow(mainNode.getWifiRange(), 2);
        boolean result = x2 + y2 <= r2 + 1;
        return result;
    }

    public static boolean checkIfCirclesIntersect(Node mainNode, Node subNode) {
        double distance = countDistanceBetweenNodes(mainNode, subNode);
        return (distance != 0)
                && (mainNode.getWifiRange() != subNode.getWifiRange())
                && (distance > abs(mainNode.getWifiRange() - subNode.getWifiRange()));
    }
}
