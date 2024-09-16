package com.org.seerbitbanking.service.impl;

import com.org.seerbitbanking.dto.request.TransactionRequest;
import com.org.seerbitbanking.dto.response.StatsResponse;
import com.org.seerbitbanking.model.Stats;
import com.org.seerbitbanking.model.Transaction;
import com.org.seerbitbanking.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    @Override
    public int addTransaction(TransactionRequest request) {
        log.info("Passed transaction {}", request);
        try {
            BigDecimal amount = new BigDecimal(request.getAmount());
            ZonedDateTime timestamp = ZonedDateTime.parse(request.getTimestamp());

            if (timestamp.isAfter(ZonedDateTime.now())) {
                return HttpStatus.UNPROCESSABLE_ENTITY.value();
            }

            if (timestamp.isBefore(ZonedDateTime.now().minusSeconds(30))) {
                return HttpStatus.NO_CONTENT.value();
            }

            transactions.add(new Transaction(amount, timestamp));
            return HttpStatus.CREATED.value();
        } catch (NumberFormatException | DateTimeParseException ex) {
            log.error("Failed to parse date ", ex);
            return HttpStatus.UNPROCESSABLE_ENTITY.value();
        } catch (Exception ex) {
            log.error("Failed to process transaction ", ex);
            return HttpStatus.BAD_REQUEST.value();
        }
    }

    @Override
    public StatsResponse getStats() {
        Stats stats = new Stats();
        ZonedDateTime now = ZonedDateTime.now();
        for (Transaction t : transactions) {
            if (t.getTimestamp().isAfter(now.minusSeconds(30))) {
                stats.updateStats(t.getAmount());
            }
        }
        return new StatsResponse(stats);
    }

    @Override
    public void deleteAllTransactions() {
        transactions.clear();
        log.info("Transactions deleted successfully at {}", LocalDateTime.now());
    }
}
