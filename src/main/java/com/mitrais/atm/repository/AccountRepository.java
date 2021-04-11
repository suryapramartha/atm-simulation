package com.mitrais.atm.repository;

import com.mitrais.atm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByAccNumberAndPin(String accNumber, String pin);
}
