package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.AccountRepository;
import com.mitrais.atm.repository.TransactionRepository;
import com.mitrais.atm.util.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Map<String, Object> processWithdraw(String amount) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Account account = accountService.getLoggedAccount();
        try {
            dataValidationService.checkWithdrawAmount(amount);
            Transaction newTransaction = new Transaction(account.getAccNumber(), WITHDRAW, LocalDate.now(), amount, account.getBalance());
            account.setBalance(account.getBalance() - Integer.parseInt(newTransaction.getAmount()));
            accountRepository.save(account);
            transactionRepository.save(newTransaction);
            result.put("account", account);
            result.put("transaction", newTransaction);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    @Override
    public Map<String,Object> processFundTransfer(String dest, String amount) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Account origin = accountService.getLoggedAccount();
        try {
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
        }
        catch (Exception e) {
            throw e;
        }
        return result;
    }

    @Override
    public List<Transaction> getTransactionHistory(String accNumber, int limit) {
        return transactionRepository.findByAccountNumberOrderByTransactionDateDesc(accNumber).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionHistoryOnDate(String accNumber, LocalDate date, int limit) {
        return transactionRepository.findByAccountNumberAndTransactionDateOrderByTransactionDateDesc(accNumber,date).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

}
