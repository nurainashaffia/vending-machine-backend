package com.aina.vending_machine.service;

import com.aina.vending_machine.exception.InsufficientCapacityException;
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
                        " has a total of " + item.getItemStock() + " remaining stock quantity");
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

    public Slot updateSlot(Long slotId, Integer capacity, SlotStatus slotStatus, Long itemId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with Id " + slotId));

        Item item = slot.getItem();

        if (itemId != null) {
            item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found with Id " + itemId));

            slot.setItem(item);
        }

        if (capacity != null) {
            if (capacity > fullCapacity) {
                throw new InsufficientCapacityException("The maximum capacity for a slot is " + fullCapacity);
            }

            int qtyToRestock = capacity - slot.getCapacity();
            if (qtyToRestock > 0) {
                if (item == null) {
                    throw new ResourceNotFoundException("No item is selected.");
                }
                if (item.getItemStock() < qtyToRestock) {
                    throw new InsufficientStockException("Insufficient stock! Item with Id " + item.getItemId() +
                            " has only " + item.getItemStock() + " remaining stock quantity");
                }

                item.setItemStock(item.getItemStock() - qtyToRestock);
                slot.setCapacity(capacity);

                itemRepository.save(item);
            } else {
                throw new IllegalArgumentException("Capacity inserted is lower than the current capacity");
            }
        }

        if (slotStatus != null) {
            slot.setSlotStatus(slotStatus);
        }

        slotRepository.save(slot);
        return slot;
    }

    public Slot updateSlotStatus(Long slotId, SlotStatus slotStatus) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with Id " + slotId));

        slot.setSlotStatus(slotStatus);
        slotRepository.save(slot);

        return slot;
    }

    public Slot deleteItemBySlotId(Long slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with Id " + slotId));

        if (slot.getItem() != null) {
            slot.setItem(null);
            slot.setCapacity(0);
            slot.setSlotStatus(SlotStatus.AVAILABLE);
            slot.setLastRestocked(null);
        }

        return slotRepository.save(slot);
    }
}
