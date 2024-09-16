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
    public ResponseEntity<Void> addTransaction(@RequestBody TransactionRequest request) {
        try {
            BigDecimal amount = new BigDecimal(request.getAmount());
            ZonedDateTime timestamp = ZonedDateTime.parse(request.getTimestamp());

            if (timestamp.isAfter(ZonedDateTime.now())) {
                return ResponseEntity.status(422).build();
            }

            Transaction transaction = new Transaction(amount, timestamp);
            if (timestamp.isBefore(ZonedDateTime.now().minusSeconds(30))) {
                return ResponseEntity.noContent().build();
            }

            transactionService.addTransaction(transaction);
            return ResponseEntity.status(201).build();
        } catch (NumberFormatException | DateTimeParseException e) {
            return ResponseEntity.status(422).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping
    public ResponseEntity<StatsResponse> getStatistics() {
        Stats statistics = transactionService.getStats();
        return ResponseEntity.ok(new StatsResponse(statistics));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTransactions() {
        transactionService.deleteAllTransactions();
        return ResponseEntity.noContent().build();
    }
}
