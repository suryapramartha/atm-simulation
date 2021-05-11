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
        generateTransactions(accNumber, initialRowData);
    }

    @Test
    public void checkDataDidSaved() {
        transactions = transactionRepository.findAll();
        assertEquals(transactions.size(), initialRowData);
    }
    @Test
    public void givenTransactionRepositoryWhenSavedThenReturnOK() {
        Transaction transaction = transactionRepository.save(new Transaction());
        Transaction existingTrans = transactionRepository.findById(transaction.getTransactionId()).get();

        assertEquals(transaction.getTransactionId(), existingTrans.getTransactionId());
    }

    @Test
    public void givenValidInputWhenGetTransactionHistoryThenReturnLastXTransaction() {

        //act
        int limit = 10;
        Transaction transaction = generateTransaction(accNumber, 1);
        transaction.setTransactionDate(LocalDate.of(2022,10,11));
        transactionRepository.save(transaction);

        //act
        List<Transaction> result = transactionService.getTransactionHistory(accNumber, limit);

        //assert
        assertEquals(result.get(0), transaction);

    }
    @Test
    public void givenValidInputWhenGetTransactionHistoryOnDateThenReturnLastXTransaction() {
        int limit = 10;
        LocalDate date = LocalDate.now();
        List<Transaction> result = transactionService.getTransactionHistoryOnDate(accNumber,date, limit);
        assertEquals(result.get(0).getTransactionDate(), date);

    }

    private void generateTransactions(String accNumber, int row) {
        for ( int i = 0; i < row; i++) {
            Transaction newTrans = generateTransaction(accNumber, i);
            transactionRepository.save(newTrans);
        }
    }

    private Transaction generateTransaction(String accNumber, int i) {
        Transaction newTrans = new Transaction();
        newTrans.setAccountNumber(accNumber);
        newTrans.setRefNo("111111");
        newTrans.setTransactionDate(LocalDate.now().plusDays(i));
        newTrans.setAmount("10");
        newTrans.setTransactionType("WITHDRAW");
        newTrans.setBalance(10 + i);
        return newTrans;
    }


}
