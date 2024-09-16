package com.org.seerbitbanking.service.impl;

import com.org.seerbitbanking.model.Stats;
import com.org.seerbitbanking.model.Transaction;
import com.org.seerbitbanking.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    @Override
    public void addTransaction(Transaction transaction) {
        log.info("Passed transaction {}", transaction);
        ZonedDateTime now = ZonedDateTime.now();
        if (transaction.getTimestamp().isAfter(now.minusSeconds(30))) {
            transactions.add(transaction);
        }
    }

    @Override
    public Stats getStats() {
        Stats stats = new Stats();
        ZonedDateTime now = ZonedDateTime.now();
        for (Transaction t : transactions) {
            if (t.getTimestamp().isAfter(now.minusSeconds(30))) {
                stats.updateStats(t.getAmount());
            }
        }
        return stats;
    }

    @Override
    public void deleteAllTransactions() {
        transactions.clear();
        log.info("Transactions deleted successfully at {}", LocalDateTime.now());
    }
}
