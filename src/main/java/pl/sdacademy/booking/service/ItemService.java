package pl.sdacademy.booking.service;

import lombok.extern.slf4j.Slf4j;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.ItemDto;
import pl.sdacademy.booking.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDto> findItems() {
        log.info("findItems");
        List<ItemDto> result = new ArrayList<>();

        List<ItemEntity> itemEntities = itemRepository.findItems();
        for (ItemEntity entity : itemEntities) {
            result.add(ItemDto.builder()
                    .name(entity.getName())
                    .price(entity.getPrice())
                    .description(entity.getDescription())
                    .build());
        }
        return result;
    }
}
