package com.org.seerbitbanking.controller;

import com.org.seerbitbanking.dto.request.TransactionRequest;
import com.org.seerbitbanking.dto.response.StatsResponse;
import com.org.seerbitbanking.model.Stats;
import com.org.seerbitbanking.model.Transaction;
import com.org.seerbitbanking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping(TransactionStatsController.API_BASE)
public class TransactionStatsController {

    public static final String API_BASE = "/api/v1/transaction-stats";


    private final TransactionService transactionService;

    public TransactionStatsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request) {
        return ResponseEntity.status(transactionService.addTransaction(request)).build();
    }

    @GetMapping
    public ResponseEntity<StatsResponse> getStatistics() {
        return ResponseEntity.ok(transactionService.getStats());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTransactions() {
        transactionService.deleteAllTransactions();
        return ResponseEntity.noContent().build();
    }
}
