package com.mitrais.atm.controller;


import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.DataValidationService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private DataValidationService dataValidationService;

    private static Account origin;

    @Before
    public void setUp() {
        origin = new Account();
        origin.setAccNumber("111111");
        origin.setPin("111111");
        origin.setName("origin");
        origin.setBalance(100);
    }

    @Test
    public void givenValidInputWhenLoginThenReturnOK() throws Exception {
        Mockito.when(dataValidationService.checkLoginCredential(anyString(), anyString())).thenReturn(null);
        Mockito.when(accountService.getAccount(anyString(), anyString())).thenReturn(origin);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                    .param("accNumber",anyString())
                    .param("accPin", anyString()))
                .andExpect(status().isOk())
                .andExpect(view().name("screen/transactionScreen"))
                .andExpect(model().attribute("account", origin))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
