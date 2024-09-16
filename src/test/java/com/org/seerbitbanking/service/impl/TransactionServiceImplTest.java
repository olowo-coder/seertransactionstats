package com.org.seerbitbanking.service.impl;

import com.org.seerbitbanking.dto.request.TransactionRequest;
import com.org.seerbitbanking.dto.response.StatsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionServiceImplTest {

    private TransactionServiceImpl transactionServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        transactionServiceImplUnderTest = new TransactionServiceImpl();
    }

    @Test
    void testAddTransaction() {
        final TransactionRequest request = new TransactionRequest();
        request.setAmount("amount");
        request.setTimestamp("timestamp");
        final int result = transactionServiceImplUnderTest.addTransaction(request);

        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetStats() {
        final StatsResponse result = transactionServiceImplUnderTest.getStats();
    }

    @Test
    void testDeleteAllTransactions() {
        transactionServiceImplUnderTest.deleteAllTransactions();
    }
}
