package com.mitrais.atm.controller;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.TransactionService;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    private static Account origin;
    private static Transaction transaction;
    private static List<Transaction> transactionList;

    @Before
    public void setUp() {
        origin = new Account();
        origin.setAccNumber("111111");
        origin.setPin("111111");
        origin.setName("origin");
        origin.setBalance(100);

        transaction = new Transaction();
        transaction.setAccountNumber(origin.getAccNumber());
        transaction.setAmount("50");
        transaction.setRefNo("999999");
        transaction.setTransactionDate(LocalDate.now());

        transactionList = IntStream
                .range(0, 10)
                .mapToObj(i -> transaction)
                .collect(Collectors.toList());
    }

    @Test
    public void givenAccessToHomePageThenReturnOK() throws Exception {
        Mockito.when(accountService.getLoggedAccount()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("account", accountService.getLoggedAccount()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void givenAccessToWithdrawPageThenReturnOK() throws Exception {
        Mockito.when(accountService.getLoggedAccount()).thenReturn(origin);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/withdraw"))
                .andExpect(status().isOk())
                .andExpect(view().name("screen/withdrawScreen"))
                .andExpect(model().attribute("account", accountService.getLoggedAccount()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void givenAccessToFundTransferPageThenReturnOK() throws Exception {
        Mockito.when(accountService.getLoggedAccount()).thenReturn(origin);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/fund-transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("screen/fundTransferScreen"))
                .andExpect(model().attribute("account", accountService.getLoggedAccount()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void givenAccessToHistoryPageThenReturnOK() throws Exception {
        Mockito.when(accountService.getLoggedAccount()).thenReturn(origin);
        Mockito.when(transactionService.getTransactionHistory(anyString(), anyInt())).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/histories"))
                .andExpect(status().isOk())
                .andExpect(view().name("screen/transactionHistoryScreen"))
                .andExpect(model().attribute("account", accountService.getLoggedAccount()))
                .andExpect(model().attributeExists("transactionList"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void givenAccessToTransactionPageThenReturnOK() throws Exception {
        Mockito.when(accountService.getLoggedAccount()).thenReturn(origin);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("screen/transactionScreen"))
                .andExpect(model().attribute("account", accountService.getLoggedAccount()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
