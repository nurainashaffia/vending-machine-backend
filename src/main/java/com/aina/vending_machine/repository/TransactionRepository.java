package com.aina.vending_machine.repository;

import com.aina.vending_machine.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionRepository extends JpaRepository <Transaction, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Transaction t SET t.item = NULL WHERE t.item.itemId = :itemId")
    void clearItemFromTransactions(Long itemId);
}
