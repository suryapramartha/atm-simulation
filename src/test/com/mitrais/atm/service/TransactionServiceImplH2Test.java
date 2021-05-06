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

    List<Transaction> transactions;

    @Autowired
    private TransactionRepository transactionRepository;

    @Before
    public void setUp() {
         for ( int i = 0; i < 10; i++) {
             Transaction newTrans = new Transaction();
             newTrans.setAccountNumber("111111");
             newTrans.setRefNo("111111");
             newTrans.setTransactionDate(LocalDate.now());
             newTrans.setAmount("10");
             newTrans.setTransactionType("WITHDRAW");
             newTrans.setBalance(10 + i);
             transactionRepository.save(newTrans);
         }
         transactions = transactionRepository.findAll();
    }

    @Test
    public void givenTransactionRepositoryWhenSavedThenReturnOK() {
        Transaction transaction = transactionRepository.save(new Transaction());
        Transaction existingTrans = transactionRepository.findById(transaction.getTransactionId()).get();

        assertNotNull(existingTrans);
        assertEquals(transaction.getTransactionId(), existingTrans.getTransactionId());
    }

    @Test
    public void testdata() {
       // List<Transaction> existingTrans = transactionRepository.findAll();
        assertNotNull(transactions);
        assertEquals(transactions.size(), 10);

    }
}
