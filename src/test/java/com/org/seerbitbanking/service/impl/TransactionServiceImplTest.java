package com.org.seerbitbanking.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.org.seerbitbanking.dto.request.TransactionRequest;
import com.org.seerbitbanking.dto.response.StatsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TransactionServiceImplTest {

    private TransactionServiceImpl transactionServiceImplUnderTest;

    private ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    @BeforeEach
    void setUp() {
        transactionServiceImplUnderTest = new TransactionServiceImpl();
    }

    @Test
    void testAddTransaction() throws JsonProcessingException {
        final TransactionRequest request = new TransactionRequest();
        request.setAmount("100.00");
        request.setTimestamp(Instant.now().minusSeconds(20).toString());
        final int result = transactionServiceImplUnderTest.addTransaction(MAPPER.writeValueAsString(request));

        assertThat(result).isEqualTo(201);
    }

    @Test
    void testAddTransactionIsBefore30() throws JsonProcessingException {
        final TransactionRequest request = new TransactionRequest();
        request.setAmount("100.00");
        request.setTimestamp(Instant.now().minusSeconds(35).toString());
        final int result = transactionServiceImplUnderTest.addTransaction(MAPPER.writeValueAsString(request));

        assertThat(result).isEqualTo(204);
    }

    @Test
    void testAddTransactionIsAfter30() throws JsonProcessingException {
        final TransactionRequest request = new TransactionRequest();
        request.setAmount("100.00");
        request.setTimestamp(Instant.now().plusSeconds(35).toString());
        final int result = transactionServiceImplUnderTest.addTransaction(MAPPER.writeValueAsString(request));

        assertThat(result).isEqualTo(422);
    }

    @Test
    void testAddTransactionCannotParseDate() throws JsonProcessingException {
        final TransactionRequest request = new TransactionRequest();
        request.setAmount("100.00");
        request.setTimestamp("2018-07-17T09");
        final int result = transactionServiceImplUnderTest.addTransaction(MAPPER.writeValueAsString(request));

        assertThat(result).isEqualTo(422);
    }

    @Test
    void testAddTransactionCannotInvalidJson() {
        final int result = transactionServiceImplUnderTest.addTransaction("{badjson");
        assertThat(result).isEqualTo(400);
    }

    @Test
    void testGetStats() throws JsonProcessingException {
        final TransactionRequest request = new TransactionRequest();
        request.setAmount("100.00");
        request.setTimestamp(Instant.now().minusSeconds(20).toString());
        transactionServiceImplUnderTest.addTransaction(MAPPER.writeValueAsString(request));

        final StatsResponse result = transactionServiceImplUnderTest.getStats();
        System.out.println(result);
        assertEquals("100.00", result.getSum().toString());
        assertEquals("100.00", result.getAvg().toString());
        assertEquals("100.00", result.getMax().toString());
        assertEquals("100.00", result.getMin().toString());
        assertEquals(1, result.getCount());
    }

    @Test
    public void testDeleteTransactions() {
        transactionServiceImplUnderTest.deleteAllTransactions();
        assertEquals(0, transactionServiceImplUnderTest.getStats().getCount());
    }
}
