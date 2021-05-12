package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.repository.TransactionRepository;
import com.mitrais.atm.util.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final String WITHDRAW = "WITHDRAW";
    private static final String FUND_TRANSFER = "FUND TRANSFER";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DataValidationService dataValidationService;

    @Autowired
    private RandomNumberGenerator randomNumberGenerator;

    @Transactional
    @Override
    public Map<String, Object> processWithdraw(String amount) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Account account = accountService.getLoggedAccount();

        dataValidationService.checkWithdrawAmount(amount);
        Transaction newTransaction = new Transaction(account.getAccNumber(), WITHDRAW, LocalDate.now(), amount, account.getBalance());
        account.setBalance(account.getBalance() - Integer.parseInt(newTransaction.getAmount()));
        accountRepository.save(account);
        transactionRepository.save(newTransaction);
        result.put("account", account);
        result.put("transaction", newTransaction);

        return result;
    }

    @Transactional
    @Override
    public Map<String,Object> processFundTransfer(String dest, String amount) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Account origin = accountService.getLoggedAccount();

        dataValidationService.checkFundInputData(dest, amount);
        Account destAccount = accountService.getAccountByAccNumber(dest);
        String refNo = randomNumberGenerator.getRandom6DigitNumber();
        Transaction newFundTransferTrans = new Transaction(origin.getAccNumber(), dest, FUND_TRANSFER, LocalDate.now(), amount, origin.getBalance(), refNo);
        origin.setBalance(origin.getBalance() - Integer.parseInt(newFundTransferTrans.getAmount()));
        destAccount.setBalance(destAccount.getBalance() + Integer.parseInt(newFundTransferTrans.getAmount()));

        accountRepository.save(origin);
        accountRepository.save(destAccount);
        transactionRepository.save(newFundTransferTrans);

        result.put("account", origin);
        result.put("transaction", newFundTransferTrans);
        return result;
    }

    @Override
    public List<Transaction> getTransactionHistory(String accNumber, int limit) {
        Page<Transaction> transactions = transactionRepository.findAll(hasAccountNumber(accNumber), PageRequest.of(0, limit, Sort.by("transactionDate").descending()));
        return transactions.getContent();
    }

    @Override
    public List<Transaction> getTransactionHistoryOnDate(String accNumber, LocalDate date, int limit) {
        Page<Transaction> transactions = transactionRepository.findAll(hasAccountNumberAndTransactionDate(accNumber, date), PageRequest.of(0, limit, Sort.by("transactionDate").descending()));

        return transactions.getContent();
    }

    static Specification<Transaction> hasAccountNumber(String accNumber) {
        return (transaction, query, criteriaBuilder) -> criteriaBuilder.equal(transaction.get("accountNumber"),accNumber);
    }

    static Specification<Transaction> hasAccountNumberAndTransactionDate(String accNumber, LocalDate date) {
        return (transaction, query, criteriaBuilder) -> {
            Predicate accNumberPredicate = criteriaBuilder.equal(transaction.get("accountNumber"), accNumber);
            Predicate transactionDatePredicate = criteriaBuilder.equal(transaction.get("transactionDate"), date);
            return criteriaBuilder.and(accNumberPredicate, transactionDatePredicate);
        };
    }

}
