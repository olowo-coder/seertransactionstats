package com.org.seerbitbanking.service;

import com.org.seerbitbanking.dto.request.TransactionRequest;
import com.org.seerbitbanking.dto.response.StatsResponse;
import com.org.seerbitbanking.model.Stats;
import com.org.seerbitbanking.model.Transaction;

public interface TransactionService {

    int addTransaction(String jsonPayload);

    StatsResponse getStats();

    void deleteAllTransactions();
}
