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

    public Node divideNodeAndReturnNew(double divider) {
        return new Node(this.getX() / divider, this.getY() / divider, this.getWifiRange());
    }

    public Node addToNodeAndReturnNew(double add) {
        return new Node(this.getX() + add, this.getY() + add, this.getWifiRange());
    }

    public Node subtractToNodeAndReturnNew(double subtract) {
        return new Node(this.getX() - subtract, this.getY() - subtract, this.getWifiRange());
    }

    public Node subtractNode(Node node) {
        return new Node(this.getX() - node.getX(), this.getY() - node.getY(), this.getWifiRange());
    }

    public Node addNode(Node node) {
        return new Node(this.getX() + node.getX(), this.getY() + node.getY(), this.getWifiRange());
    }

    public Node sqrt() {
        return new Node(Math.sqrt(this.getX()), Math.sqrt(this.getY()), this.getWifiRange());
    }

    public Node pow(int a) {
        return new Node(Math.pow(this.getX(), a), Math.pow(this.getY(), a), this.getWifiRange());
    }
}
