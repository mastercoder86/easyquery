package com.shopping_centre.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_centre.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
