package com.best.friends.bachelor.service;

import com.best.friends.bachelor.main.counting.NodeLocator;
import com.best.friends.bachelor.main.counting.NodeLocatorResponse;
import com.best.friends.bachelor.model.IterationServiceResponse;
import com.best.friends.bachelor.node.CountingUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NodeLocatorIterationService {
    private NodeLocator nodeLocator;

    public NodeLocatorIterationService() {
        nodeLocator = new NodeLocator();
    }

    private List<NodeLocatorResponse> countIteration(int quantity, double countingError, int iterationQuantity) {
        List<NodeLocatorResponse> results = new ArrayList<>();
        for (int i = 0; i < iterationQuantity; i++) {
            nodeLocator.initiate(quantity, countingError, 0);
            results.add(nodeLocator.start());
        }
        return results;
    }

    public List<IterationServiceResponse> countAndPrepareResponse(int quantityFrom, int quantityTo, double countingError, int iterationQuantity) {
        List<IterationServiceResponse> responses = new ArrayList<>();
        for (int i = quantityFrom; i <= quantityTo; i++) {
            Map<Integer, Double> resultTable = new LinkedHashMap<>();
            double min = 1000000;
            double max = 0;
            double average = 0;
            int iteration = 1;
            List<NodeLocatorResponse> results = countIteration(i, countingError, iterationQuantity);
            for (NodeLocatorResponse nodeLocatorResponse : results) {
                double precision = CountingUtils.countDistanceBetweenNodes(nodeLocatorResponse.getMainNode(), nodeLocatorResponse.getMainNodeLoc());
                if (precision > max) {
                    max = precision;
                } else if (precision < min) {
                    min = precision;
                }
                average += precision;
                resultTable.put(iteration++, precision);
            }
            average = average / results.size();
            responses.add(new IterationServiceResponse(min, max, average, resultTable));
        }
        return responses;
    }

}
