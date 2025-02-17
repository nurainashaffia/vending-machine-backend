package com.aina.vending_machine.controller;

import com.aina.vending_machine.model.Item;
import com.aina.vending_machine.service.ItemService;
import com.aina.vending_machine.repository.ItemRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RequestMapping("/api/items")
@RestController
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @PostMapping("/save")
    public ResponseEntity<Item> saveItem(@RequestBody Item item) {
        Item newItem = itemService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }

    @GetMapping("/find-all")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/find/{itemId}")
    public ResponseEntity<Item> find(@PathVariable Long itemId) {
        Item item = itemService.findItemById(itemId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String itemName) {
        return itemRepository.findByItemNameContainingIgnoreCase(itemName);
    }

    @GetMapping("/search-itemName")
    public List<Item> searchQuery(@RequestParam String itemName) {
        return itemRepository.search(itemName);
    }

    @GetMapping("/search-restock")
    public List<Item> searchItemsToRestock(@RequestParam Long itemStock) {
        return itemRepository.findByItemStockLessThan(itemStock);
    }

    @PatchMapping("/restock/{itemId}")
    public ResponseEntity<Item> updateItemsToRestock(@PathVariable Long itemId, @RequestParam Long itemStock) {
        Item item = itemService.updateItemStockById(itemId, itemStock);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItemById(itemId);
        return ResponseEntity.noContent().build();
    }
}
