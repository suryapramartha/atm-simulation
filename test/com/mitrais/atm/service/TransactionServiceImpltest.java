package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.repository.TransactionRepository;
import com.mitrais.atm.util.RandomNumberGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImpltest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountServiceImpl accountService;

    @Mock
    private DataValidationService dataValidationService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RandomNumberGenerator randomNumberGenerator;

    private static Account origin;
    private static Account dest;
    private static Transaction transaction;
    private static List<Transaction> transactionList;

    @Before
    public void setUp() {
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

        accountService.setLoggedAccount(origin);

        transaction = new Transaction();
        transaction.setAccountNumber(origin.getAccNumber());
        transaction.setDescAccountNumber(dest.getAccNumber());
        transaction.setAmount("50");
        transaction.setRefNo("999999");
        transaction.setTransactionDate(LocalDate.now());

        transactionList = IntStream
                .range(0, 10)
                .mapToObj(i -> transaction)
                .collect(Collectors.toList());
    }

    @Test
    public void givenValidInputWhenProcessWithdrawThenReturnAccount() throws Exception {
        int balanceBeforeTransaction = origin.getBalance();
        String amount = "10";
        when(accountService.getLoggedAccount()).thenReturn(origin);
        when(dataValidationService.checkWithdrawAmount(amount)).thenReturn(null);
        Map<String, Object> result = transactionService.processWithdraw(amount);

        Account updatedAcc = (Account) result.get("account");
        Transaction transaction = (Transaction) result.get("transaction");

        assertThat(updatedAcc.getBalance(), is(balanceBeforeTransaction - Integer.parseInt(transaction.getAmount())));
    }

    @Test
    public void givenValidInputWhenProcessFundTransferThenReturnAccount() throws Exception {
        String amount = "10";
        String descAccount = dest.getAccNumber();
        int balanceOriginBeforeTransaction = origin.getBalance();
        int balanceDestBeforeTransaction = dest.getBalance();

        when(accountService.getLoggedAccount()).thenReturn(origin);
        when(dataValidationService.checkFundInputData(descAccount,amount)).thenReturn(null);
        when(accountService.getAccountByAccNumber(descAccount)).thenReturn(dest);
        when(randomNumberGenerator.getRandom6DigitNumber()).thenReturn("111111");
        when(accountRepository.save(any(Account.class))).thenReturn(origin);
        Map<String, Object> result = transactionService.processFundTransfer(descAccount, amount);

        Account updatedAcc = (Account) result.get("account");
        Transaction transaction = (Transaction) result.get("transaction");

        assertThat(updatedAcc.getBalance(), is(balanceOriginBeforeTransaction - Integer.parseInt(transaction.getAmount())));
        assertThat(dest.getBalance(), is(balanceDestBeforeTransaction + Integer.parseInt(transaction.getAmount())));

    }

    @Test
    public void givenValidInputWhenGetTransactionHistoryThenReturnListOfTransaction() {
        int limit = 10;
        when(transactionRepository.findByAccountNumberOrderByTransactionDateDesc(anyString())).thenReturn(transactionList);

        List<Transaction> transactions = transactionService.getTransactionHistory("112233", limit);
        assertThat(transactions.size(), is(limit));
    }

    @Test
    public void givenValidInputWhenGetTransactionHistoryWithDateThenReturnListOfTransaction() {
        int limit = 10;
        LocalDate date = LocalDate.now();
        when(transactionRepository.findByAccountNumberAndTransactionDateOrderByTransactionDateDesc(anyString(), any(LocalDate.class))).thenReturn(transactionList);

        List<Transaction> transactions = transactionService.getTransactionHistoryOnDate("112233",date, limit);
        assertThat(transactions.size(), is(limit));
    }
}
