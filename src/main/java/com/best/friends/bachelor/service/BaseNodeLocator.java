package com.best.friends.bachelor.service;

import com.best.friends.bachelor.main.counting.NodeLocatorResponse;

public interface BaseNodeLocator {
    NodeLocatorResponse generateAndLocateNodes(int anchorNodeQuantity, double calculationError, int iterationQuantity);
}
