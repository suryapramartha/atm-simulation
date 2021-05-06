package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    private static Account account;

    @Before
    public void setUp() {
        account = new Account();
        account.setAccNumber("121212");
        account.setBalance(100);
        account.setName("test");
        account.setPin("111111");
    }

    @Test
    public void givenValidAccNumberAndPinWhenGetAccountThenReturnAccount() {
        when(accountRepository.findByAccNumberAndPin(account.getAccNumber(), account.getPin())).thenReturn(account);
        Account existingAcc = accountService.getAccount(account.getAccNumber(),account.getPin());

        assertThat(account.getAccNumber(), is(existingAcc.getAccNumber()));
        assertThat(account.getName(), is(existingAcc.getName()));
    }
    @Test
    public void givenValidAccNumberWhenGetAccountThenReturnAccount() throws Exception {
        when(accountRepository.findById(account.getAccNumber())).thenReturn(Optional.of(account));
        Account existingAcc = accountService.getAccountByAccNumber(account.getAccNumber());

        assertThat(account.getAccNumber(), is(existingAcc.getAccNumber()));
        assertThat(account.getName(), is(existingAcc.getName()));
    }
    @Test(expected = Exception.class)
    public void givenInvalidAccNumberWhenGetAccountThenReturnError() throws Exception {
        when(accountRepository.findById(account.getAccNumber())).thenThrow(new Exception("Data not found"));
        accountService.getAccountByAccNumber(account.getAccNumber());
    }
}
