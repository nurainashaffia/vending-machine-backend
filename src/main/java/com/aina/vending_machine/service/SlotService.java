package com.aina.vending_machine.service;

import com.aina.vending_machine.model.Item;
import com.aina.vending_machine.model.Slot;
import com.aina.vending_machine.enums.SlotStatus;
import com.aina.vending_machine.repository.ItemRepository;
import com.aina.vending_machine.repository.SlotRepository;
import com.aina.vending_machine.exception.InsufficientStockException;
import com.aina.vending_machine.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Value("${slot.fullCapacity}")
    private int fullCapacity;

    public void createSlot(Slot slot) {
        Item item = slot.getItem();

        if (item != null) {
            Long itemId = item.getItemId();

            item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found with Id " + itemId));

            if (item.getItemStock() < slot.getCapacity()) {
                throw new InsufficientStockException("Insufficient stock! Item with Id " + itemId +
                        " has a total of " + item.getItemStock() + " remaining stock quantity.");
            } else {
                slot.setItem(item);
                item.setItemStock(item.getItemStock() - slot.getCapacity());
            }
        }

        slot.setLastRestocked(LocalDateTime.now());
        slotRepository.save(slot);
    }

    public Slot findSlotById(Long slotId) {
        return slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with Id " + slotId));
    }

    public Slot updateSlotBySlotId(Long slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with Id " + slotId));

        if (slot.getSlotStatus() == SlotStatus.AVAILABLE) {
            Item item = slot.getItem();

            if (item == null) {
                item = itemRepository.findFirstByItemStockGreaterThanEqual(slot.getCapacity())  // Changed: Simplified this check
                        .orElseThrow(() -> new ResourceNotFoundException("No available items with sufficient stock"));

                slot.setItem(item);
            }

            int qtyToRestock = fullCapacity - slot.getCapacity();

            if (item.getItemStock() >= qtyToRestock) {
                slot.setCapacity(fullCapacity);
                item.setItemStock(item.getItemStock() - qtyToRestock);

                slotRepository.save(slot);
                itemRepository.save(item);
            } else {
                throw new InsufficientStockException("Insufficient stock! Item with Id " + item.getItemId() +
                        " has a total of " + item.getItemStock() + " remaining stock quantity.");
            }
        }

        return slot;
    }

    public void deleteSlotById(Long slotId) {
        Slot slot = slotRepository.findById(slotId)
                        .orElseThrow(() -> new ResourceNotFoundException("Slot not found with Id " + slotId));

        slotRepository.delete(slot);
    }
}
