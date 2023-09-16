package pl.sdacademy.booking.repository;

import pl.sdacademy.booking.data.ItemEntity;

import java.util.List;

public interface ItemRepository {

    List<ItemEntity> findItems();
    void addItem(ItemEntity item);

   Long findItemByName(String name);
}