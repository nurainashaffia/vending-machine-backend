package com.aina.vending_machine.repository;

import com.aina.vending_machine.model.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ItemRepository extends JpaRepository <Item, Long> {
    @Query("select i from Item i where lower(i.itemName) like lower(concat('%',:itemName,'%'))")
    public List<Item> search(String itemName);

    List<Item> findByItemNameContainingIgnoreCase(String itemName);

    List<Item> findByItemStockLessThan(Long itemStock);

    Optional<Item> findFirstByItemStockGreaterThanEqual(int stock);
}
