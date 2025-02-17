package com.aina.vending_machine.repository;

import com.aina.vending_machine.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository <Transaction, Long> {
}
