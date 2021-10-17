package com.best.friends.bachelor.controller;

import com.best.friends.bachelor.service.impl.NodeLocatorService;
import com.best.friends.bachelor.main.counting.NodeLocatorResponse;
import com.best.friends.bachelor.model.IterationServiceResponse;
import com.best.friends.bachelor.service.impl.NodeLocatorIterationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nodeLocator")
@CrossOrigin(origins = "localhost:4200")
public class NodeController {
    private NodeLocatorService nodeLocatorService = new NodeLocatorService();
    private NodeLocatorIterationService nodeLocatorIterationService = new NodeLocatorIterationService();

    @GetMapping("locate/{quantity}/{calculationError}/{iterationQuantity}")
    public ResponseEntity<NodeLocatorResponse> locateNodes(@PathVariable int quantity, @PathVariable double calculationError,
                                                           @PathVariable int iterationQuantity) {
        return ResponseEntity.ok(nodeLocatorService.generateAndLocateNodes(quantity, calculationError, iterationQuantity));
    }

    @PostMapping("locate/addNode/{calculationError}/{iterationQuantity}")
    public ResponseEntity<NodeLocatorResponse> addNewNode(@RequestBody NodeLocatorResponse initData, @PathVariable double calculationError,
                                                          @PathVariable int iterationQuantity) {
        nodeLocatorService = new NodeLocatorService();
        return ResponseEntity.ok(nodeLocatorService.addNode(initData, calculationError, iterationQuantity));
    }

    @GetMapping("locate/iterative/{quantityFrom}/{quantityTo}/{calculationError}/{iterationQuantity}")
    public ResponseEntity<List<IterationServiceResponse>> iterativeComparison(@PathVariable int quantityFrom, @PathVariable int quantityTo, @PathVariable double calculationError,
                                                                             @PathVariable int iterationQuantity) {
        return ResponseEntity.ok(nodeLocatorIterationService.countAndPrepareResponse(quantityFrom, quantityTo, calculationError, iterationQuantity));
    }
}
