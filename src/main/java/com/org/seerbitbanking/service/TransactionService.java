package com.org.seerbitbanking.service;

import com.org.seerbitbanking.model.Stats;
import com.org.seerbitbanking.model.Transaction;

public interface TransactionService {

    void addTransaction(Transaction transaction);

    Stats getStats();

    void deleteAllTransactions();
}
