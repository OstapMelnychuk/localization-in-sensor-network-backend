package com.best.friends.bachelor.controller;

import com.best.friends.bachelor.main.counting.NodeLocator;
import com.best.friends.bachelor.main.counting.NodeLocatorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nodeLocator")
@CrossOrigin(origins = "localhost:4200")
public class NodeController {
    private NodeLocator nodeLocator = new NodeLocator();

    @GetMapping("locate/{quantity}/{calculationError}/{iterationQuantity}")
    public ResponseEntity<NodeLocatorResponse> locateNodes(@PathVariable int quantity, @PathVariable double calculationError,
                                                           @PathVariable int iterationQuantity) {
        nodeLocator.initiate(quantity, calculationError, iterationQuantity);
        return ResponseEntity.ok(nodeLocator.start());
    }

    @PostMapping("locate/addNode/{calculationError}/{iterationQuantity}")
    public ResponseEntity<NodeLocatorResponse> addNewNode(@RequestBody NodeLocatorResponse initData, @PathVariable double calculationError,
                                                          @PathVariable int iterationQuantity) {
        nodeLocator = new NodeLocator();
        return ResponseEntity.ok(nodeLocator.addNode(initData, calculationError, iterationQuantity));
    }
}
