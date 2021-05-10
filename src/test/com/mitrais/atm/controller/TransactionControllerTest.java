package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionServiceImpl transactionService;


    private static Account origin;
    private static Account dest;
    private static Map<String, Object> updatedAccount;
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

        updatedAccount = new HashMap<>();
        updatedAccount.put("account", origin);
        updatedAccount.put("transaction", transaction);
    }

    @Test
    public void givenValidInputWhenProcessWithdrawThenReturnOK() throws Exception {
        Mockito.when(transactionService.processWithdraw(anyString())).thenReturn(updatedAccount);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/process-withdraw")
                    .param("withdrawAmount",anyString())
                    )
                .andExpect(status().isOk())
                .andExpect(view().name("screen/summaryScreen"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void givenValidInputWhenProcessFundTransferThenReturnOK() throws Exception {
        Mockito.when(transactionService.processFundTransfer(anyString(), anyString())).thenReturn(updatedAccount);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/process-fund-transfer")
                  .param("descAcc",anyString())
                  .param("transferAmount",anyString())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("screen/summaryScreen"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void givenValidInputWhenFilterByDateThenReturnOK() throws Exception {
        Mockito.when(accountService.getLoggedAccount()).thenReturn(origin);
        Mockito.when(transactionService.getTransactionHistory(anyString(), anyInt())).thenReturn(transactionList);
        Mockito.when(transactionService.getTransactionHistoryOnDate(anyString(),any(LocalDate.class), anyInt())).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/filterByDate")
                    .param("limitFilter", "10")
                    .param("dateFilter", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("screen/transactionHistoryScreen"))
                .andExpect(model().attribute("transactionList", transactionList))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
