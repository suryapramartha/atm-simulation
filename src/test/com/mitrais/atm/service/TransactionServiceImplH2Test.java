package com.mitrais.atm.service;

import com.mitrais.atm.Main;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureTestDatabase
public class TransactionServiceImplH2Test {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    List<Transaction> transactions;
    private static final String accNumber = "111111";
    private static final int initialRowData = 50;

    @Before
    public void setUp() {
        generateTransaction(accNumber, initialRowData);
    }

    @Test
    public void checkDataDidSaved() {
        transactions = transactionRepository.findAll();
        assertNotNull(transactions);
        assertEquals(transactions.size(), initialRowData);
    }
    @Test
    public void givenTransactionRepositoryWhenSavedThenReturnOK() {
        Transaction transaction = transactionRepository.save(new Transaction());
        Transaction existingTrans = transactionRepository.findById(transaction.getTransactionId()).get();

        assertNotNull(existingTrans);
        assertEquals(transaction.getTransactionId(), existingTrans.getTransactionId());
    }

    @Test
    public void givenValidInputWhenGetTransactionHistoryThenReturnLastXTransaction() {
        int limit = 10;
        List<Transaction> result = transactionService.getTransactionHistory(accNumber, limit);
        assertEquals(result.size(), limit);
        // Generate transaction method creates loop date with plusDays(1)
        // transactionService.getTransactionHistory() is sorted with transactionDate descending
        assertEquals(result.get(0).getTransactionDate(), LocalDate.now().plusDays(initialRowData - 1));

    }
    @Test
    public void givenValidInputWhenGetTransactionHistoryOnDateThenReturnLastXTransaction() {
        int limit = 10;
        LocalDate date = LocalDate.now();
        List<Transaction> result = transactionService.getTransactionHistoryOnDate(accNumber,date, limit);
        assertEquals(result.get(0).getTransactionDate(), date);

    }

    private void generateTransaction(String accNumber, int row) {
        for ( int i = 0; i < row; i++) {
            Transaction newTrans = new Transaction();
            newTrans.setAccountNumber(accNumber);
            newTrans.setRefNo("111111");
            newTrans.setTransactionDate(LocalDate.now().plusDays(i));
            newTrans.setAmount("10");
            newTrans.setTransactionType("WITHDRAW");
            newTrans.setBalance(10 + i);
            transactionRepository.save(newTrans);
        }
    }


}
