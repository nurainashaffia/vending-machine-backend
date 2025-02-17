package com.aina.vending_machine.service;

import com.aina.vending_machine.model.Item;
import com.aina.vending_machine.repository.ItemRepository;
import com.aina.vending_machine.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(Item item) {
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        } else if (item.getItemPrice() <= 0) {
            throw new IllegalArgumentException("Item price must be greater than zero.");
        }

        itemRepository.save(item);
        return item;
    }

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with Id " + itemId));
    }

    public Item updateItemStockById(Long itemId, Long itemStockToAdd) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with Id " + itemId));

        item.setItemStock(item.getItemStock() + itemStockToAdd);
        return itemRepository.save(item);
    }

    public void deleteItemById(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("Item not found with Id " + itemId);
        }

        itemRepository.deleteById(itemId);
    }
}
