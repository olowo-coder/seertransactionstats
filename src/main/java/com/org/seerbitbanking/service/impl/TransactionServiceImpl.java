package com.org.seerbitbanking.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    private ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    @Override
    public int addTransaction(String jsonPayload) {
        log.info("Sent request {}", jsonPayload);
        try {
            TransactionRequest request = MAPPER.readValue(jsonPayload, TransactionRequest.class);
            BigDecimal amount = new BigDecimal(request.getAmount());
            Instant timestamp = Instant.parse(request.getTimestamp());

            Instant nowTime = Instant.now();
            log.info("parsedDate time {} and instantTime  {}", timestamp, nowTime);

            if (timestamp.isAfter(nowTime)) {
                return HttpStatus.UNPROCESSABLE_ENTITY.value();
            }

            if (timestamp.isBefore(nowTime.minusSeconds(30))) {
                return HttpStatus.NO_CONTENT.value();
            }

            transactions.add(new Transaction(amount, timestamp));
            return HttpStatus.CREATED.value();
        } catch (NumberFormatException | DateTimeParseException ex) {
            log.error("Failed to parse date ", ex);
            return HttpStatus.UNPROCESSABLE_ENTITY.value();
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse date ", ex);
            return HttpStatus.BAD_REQUEST.value();
        } catch (Exception ex) {
            log.error("Failed to process transaction ", ex);
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }

    @Override
    public StatsResponse getStats() {
        Stats stats = new Stats();
        Instant now = Instant.now();
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
