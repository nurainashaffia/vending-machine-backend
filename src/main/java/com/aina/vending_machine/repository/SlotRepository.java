package com.aina.vending_machine.repository;

import com.aina.vending_machine.enums.SlotStatus;
import com.aina.vending_machine.model.Slot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository <Slot, Long> {
    public List<Slot> findByCapacityLessThan(int capacity);

    public List<Slot> findByCapacity(int capacity);

    @Query("select s from Slot s join s.item i where s.slotStatus = :status and s.capacity < :capacity and i.itemStock >= s.capacity")
    public List<Slot> searchRestockableSlots(@Param("slotStatus") SlotStatus status, @Param("capacity") int capacity);

    @Query("select s from Slot s join s.item i where s.capacity < :capacity and i.itemStock < s.capacity")
    public List<Slot> searchNonRestockableSlots(@Param("capacity") int capacity);
}
