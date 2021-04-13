package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.repository.TransactionRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImpltest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private static Account origin;
    private static Account dest;
    private static Transaction transaction;

    @BeforeClass
    public static void setUp() {
        origin = new Account();
        origin.setAccNumber("111111");
        origin.setPin("111111");
        origin.setName("origin");
        origin.setBalance(100);

        dest = new Account();
        dest.setAccNumber("222222");
        dest.setPin("222222");
        dest.setName("dest");
        dest.setBalance(50);

        transaction = new Transaction();
        transaction.setAccountNumber(origin.getAccNumber());
        transaction.setDescAccountNumber(dest.getAccNumber());
        transaction.setAmount("50");
        transaction.setRefNo("999999");
        transaction.setTransactionDate(LocalDate.now());
    }

    @Test
    public void givenValidInputWhenProcessWithdrawThenReturnAccount() {
        int balanceBeforeTransaction = origin.getBalance();
        when(accountRepository.save(any(Account.class))).thenReturn(origin);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Account updatedAcc = transactionService.processWithdraw(transaction, origin);

        assertThat(updatedAcc.getBalance(), is(balanceBeforeTransaction - Integer.parseInt(transaction.getAmount())));
    }

    @Test
    public void givenValidInputWhenProcessFundTransferThenReturnAccount() {
        int balanceOriginBeforeTransaction = origin.getBalance();
        int balanceDestBeforeTransaction = dest.getBalance();

        when(accountRepository.save(any(Account.class))).thenReturn(origin);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Account updatedAcc = transactionService.processFundTransfer(transaction, origin, dest);

        assertThat(updatedAcc.getBalance(), is(balanceOriginBeforeTransaction - Integer.parseInt(transaction.getAmount())));
        assertThat(dest.getBalance(), is(balanceDestBeforeTransaction + Integer.parseInt(transaction.getAmount())));

    }
}
