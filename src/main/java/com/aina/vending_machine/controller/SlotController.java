package com.aina.vending_machine.controller;

import com.aina.vending_machine.model.Slot;
import com.aina.vending_machine.enums.SlotStatus;
import com.aina.vending_machine.service.SlotService;
import com.aina.vending_machine.repository.SlotRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

@RequestMapping("/api/slots")
@RestController
@CrossOrigin
public class SlotController {
    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private SlotService slotService;

    @Value("${slot.fullCapacity}")
    private int fullCapacity;

    @PostMapping("/save")
    public ResponseEntity<Slot> saveSlot(@RequestBody Slot slot) {
        slotService.createSlot(slot);
        return new ResponseEntity<>(slot, HttpStatus.CREATED);
    }

    @GetMapping("/find-all")
    public List<Slot> getSlots() {
        return slotRepository.findAll();
    }

    @GetMapping("/find/{slotId}")
    public ResponseEntity<Slot> find(@PathVariable Long slotId) {
        Slot slot = slotService.findSlotById(slotId);
        return ResponseEntity.ok(slot);
    }

    @GetMapping("/search-restock-all")
    public List<Slot> searchAllSlotsToRestock(@RequestParam int capacity) {
        return slotRepository.findByCapacityLessThan(capacity);
    }

    @GetMapping("/search-restock-new")
    public List<Slot> searchSlotsToRestockNewItem(@RequestParam(defaultValue = "0") int capacity) {
        return slotRepository.findByCapacity(capacity);
    }

    @GetMapping("search-restockable")
    public List<Slot> searchRestockableSlots(@RequestParam(defaultValue = "AVAILABLE") SlotStatus slotStatus, @RequestParam(defaultValue = "${slot.fullCapacity}") int capacity) {
        return slotRepository.searchRestockableSlots(slotStatus, capacity);
    }

    @GetMapping("search-non-restockable")
    public List<Slot> searchNonRestockableSlots(@RequestParam(defaultValue = "${slot.fullCapacity}") int capacity) {
        return slotRepository.searchNonRestockableSlots(capacity);
    }

    @PatchMapping("restock/{slotId}")
    public ResponseEntity<Slot> updateSlotsToRestock(@PathVariable Long slotId) {
        Slot slot = slotService.updateSlotBySlotId(slotId);
        return ResponseEntity.ok(slot);
    }

    @PatchMapping("updateStatus/{slotId}/{slotStatus}")
    public ResponseEntity<Slot> updateSlotStatus(@PathVariable Long slotId, @PathVariable SlotStatus slotStatus) {
        Slot slot = slotService.updateSlotStatus(slotId, slotStatus);
        return ResponseEntity.ok(slot);
    }

    @DeleteMapping("/delete/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long slotId) {
        slotService.deleteSlotById(slotId);
        return ResponseEntity.noContent().build();
    }
}
