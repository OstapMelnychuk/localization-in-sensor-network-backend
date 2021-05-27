package com.best.friends.bachelor.node;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Node {
    private double x;
    private double y;
    private double wifiRange;

    public Node multiplyNodeAndReturnNew(double multiplier) {
        return new Node(this.getX() * multiplier, this.getY() * multiplier, this.getWifiRange());
    }

    public Node subtractNode(Node node) {
        return new Node(this.getX() - node.getX(), this.getY() - node.getY(), this.getWifiRange());
    }

    public Node addNode(Node node) {
        return new Node(this.getX() + node.getX(), this.getY() + node.getY(), this.getWifiRange());
    }
}
