package com.best.friends.bachelor.main.counting;

import com.best.friends.bachelor.node.IterationNodeTableRow;
import com.best.friends.bachelor.node.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NodeLocatorResponse {
    private Node mainNode;
    private Node mainNodeLoc;
    private List<Node> anchorNodes;
    private List<Node> points;
    private List<Node> filteredPoints;
    private List<IterationNodeTableRow> iterationTable;

    public void saveAllFieldsInFiles() {
        saveMainNodeAndMainNodeLocInFile();
        writeAnchorNodesInFile();
        writePointsInFile(points, "points");
        writePointsInFile(filteredPoints, "filteredPoints");
    }

    private void saveMainNodeAndMainNodeLocInFile() {
        File file = new File("mainNode.txt");
        writeNodeInFile(file, mainNode);
        file = new File("mainNodeLoc.txt");
        writeNodeInFile(file, mainNodeLoc);
    }

    private void writeNodeInFile(File file, Node node) {
        BufferedWriter bufferedWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(node.getX() + " " + node.getY() + " " + node.getWifiRange());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeAnchorNodesInFile() {
        File file = new File("anchorNodes.txt");
        BufferedWriter bufferedWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Node node : anchorNodes) {
                bufferedWriter.write(node.getX() + " " + node.getY() + " " + node.getWifiRange() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writePointsInFile(List<Node> points, String fileName) {
        File file = new File(fileName + ".txt");
        BufferedWriter bufferedWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Node node : points) {
                bufferedWriter.write(node.getX() + " " + node.getY() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
