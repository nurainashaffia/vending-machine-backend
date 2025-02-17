package com.aina.vending_machine.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonPropertyOrder({"itemId", "itemName", "itemPrice", "itemStock", "slots"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private double itemPrice;
    private Long itemStock;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Slot> slots = new ArrayList<>();

    public Item() {
    }

    public Item(String itemName, double itemPrice, Long itemStock, List<Slot> slots) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.slots = slots;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
       this.itemPrice = itemPrice;
    }

    public void setItemStock(Long itemStock) {
        this.itemStock = itemStock;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    @JsonProperty("itemId")
    public Long getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public Long getItemStock() {
        return itemStock;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Item Id    :" + itemId +
                ", Item Name  : " + itemName + '\'' +
                ", Item Price :" + itemPrice +
                ", Item Stock : " + itemStock +
                '}';
    }
}
