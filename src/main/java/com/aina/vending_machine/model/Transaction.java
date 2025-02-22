package com.aina.vending_machine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId")
    @JsonBackReference
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slotId")
    @JsonBackReference
    private Slot slot;

    public Transaction() {
    }

    public Transaction(LocalDateTime transactionDate, Item item, Slot slot) {
        this.transactionDate = transactionDate;
        this.item = item;
        this.slot = slot;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Item getItem() {
        return item;
    }

    public Slot getSlot() {
        return slot;
    }

    @JsonProperty("itemId")
    public Long getItemId() {
        return item != null ? item.getItemId() : null;
    }

    @JsonProperty("slotId")
    public Long getSlotId() {
        return slot != null ? slot.getSlotId() : null;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "Transaction Id    :" + transactionId +
                ", Transaction Date  : " + transactionDate +
                '}';
    }
}
