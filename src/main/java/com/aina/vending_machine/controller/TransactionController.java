package com.aina.vending_machine.controller;

import com.aina.vending_machine.model.Slot;
import com.aina.vending_machine.model.Transaction;
import com.aina.vending_machine.service.TransactionService;
import com.aina.vending_machine.repository.ItemRepository;
import com.aina.vending_machine.repository.SlotRepository;
import com.aina.vending_machine.repository.TransactionRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RequestMapping("/api/transactions")
@RestController
@CrossOrigin
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/save")
    public ResponseEntity<Transaction> saveTransaction(@RequestParam Long itemId, @RequestParam Long slotId) {
        Transaction transaction = transactionService.createTransaction(itemId, slotId);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping("/find-all")
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/find/{transactionId}")
    public ResponseEntity<Transaction> find(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.findTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }
}
