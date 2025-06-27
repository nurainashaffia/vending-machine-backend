package com.aina.vending_machine.model;

import com.aina.vending_machine.enums.SlotStatus;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.*;

@Entity
@JsonPropertyOrder({"slotId", "slotStatus", "capacity", "lastRestocked", "itemId", "transactionId"})
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long slotId;

    private SlotStatus slotStatus;
    private int capacity = 10;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastRestocked;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId")
    @JsonBackReference
    private Item item;

    @JsonProperty("itemId")
    public Long getItemId() {
        return this.item != null ? this.item.getItemId() : null;
    }

    @JsonProperty("itemName")
    public String getItemName() {
        return item != null ? item.getItemName() : null;
    }

    @JsonProperty("itemPrice")
    public double getItemPrice() {
        return item != null ? item.getItemPrice() : 0;
    }

    @JsonProperty("itemStock")
    public double getItemStock() {
        return item != null ? item.getItemStock() : 0;
    }

    @JsonProperty("transactionId")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Long> getTransactionIds() {
        List<Long> transactionIds = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionIds.add(transaction.getTransactionId());
        }
        return transactionIds;
    }

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Slot() {
    }

    public Slot(int capacity, LocalDateTime lastRestocked, SlotStatus slotStatus, Item item, List<Transaction> transactions) {
        this.capacity = capacity;
        this.slotStatus = slotStatus;
        this.lastRestocked = lastRestocked;
        this.item = item;
        this.transactions = transactions;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
    }

    public void setLastRestocked(LocalDateTime lastRestocked) {
        this.lastRestocked = lastRestocked;
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getSlots().remove(this);
        }

        this.item = item;

        if (item != null) {
            if (item.getSlots() == null) {
                item.setSlots(new ArrayList<>());
            }
            if (!item.getSlots().contains(this)) {
                item.getSlots().add(this);
            }
        }
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getSlotId() {
        return slotId;
    }

    public int getCapacity() {
        return capacity;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public LocalDateTime getLastRestocked() {
        return lastRestocked;
    }

    public Item getItem() {
        return item;
    }

    @JsonIgnore
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Slot{" +
               "Slot Id        : " + slotId +
               ", Item Id        :" + (item != null ? item.getItemName() : "No Item") +
               ", Capacity       : " + capacity +
               ", Last restocked :" + lastRestocked +
               "}";
    }
}
