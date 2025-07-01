package com.aina.vending_machine.service;

import com.aina.vending_machine.enums.SlotStatus;
import com.aina.vending_machine.exception.InsufficientStockException;
import com.aina.vending_machine.exception.OutOfServiceException;
import com.aina.vending_machine.model.Item;
import com.aina.vending_machine.model.Slot;
import com.aina.vending_machine.model.Transaction;
import com.aina.vending_machine.repository.ItemRepository;
import com.aina.vending_machine.repository.SlotRepository;
import com.aina.vending_machine.repository.TransactionRepository;
import com.aina.vending_machine.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SlotRepository slotRepository;

    public Transaction createTransaction(Long itemId, Long slotId) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Kuala_Lumpur"));
        LocalDateTime malaysiaTime = zonedDateTime.toLocalDateTime();

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with Id " + itemId));

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with Id " + slotId));

        if (slot.getItem() == null) {
            throw new ResourceNotFoundException("Slot with Id " + slotId + " is not assigned with any item");
        } else if (slot.getSlotStatus() == SlotStatus.OUT_OF_SERVICE) {
            throw new OutOfServiceException("Slot with Id " + slotId + " is OUT OF SERVICE.");
        } else if (slot.getCapacity() <= 0) {
            throw new InsufficientStockException("Insufficient stock for item with Id " + itemId);
        } else {
            Transaction transaction = new Transaction(malaysiaTime, item, slot);
            slot.setCapacity(slot.getCapacity() - 1);
            transactionRepository.save(transaction);
            return transaction;
        }
    }

    public Transaction findTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
    }

    public void deleteTransactionById(Long transactionId) {
        if (!transactionRepository.existsById((transactionId))) {
            throw new ResourceNotFoundException("Transaction not found with Id " + transactionId);
        }

        transactionRepository.deleteById(transactionId);
    }
}
