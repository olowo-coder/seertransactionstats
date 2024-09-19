package com.org.seerbitbanking.controller;

import com.org.seerbitbanking.dto.response.StatsResponse;
import com.org.seerbitbanking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TransactionStatsController {


    private final TransactionService transactionService;

    public TransactionStatsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> addTransaction(@RequestBody String request) {
        return ResponseEntity.status(transactionService.addTransaction(request)).build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatsResponse> getStatistics() {
        return ResponseEntity.ok(transactionService.getStats());
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<?> deleteTransactions() {
        transactionService.deleteAllTransactions();
        return ResponseEntity.noContent().build();
    }
}
