package pl.sdacademy.booking.service;

import lombok.extern.slf4j.Slf4j;
import pl.sdacademy.booking.data.ItemAttributeEntity;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.ItemDto;
import pl.sdacademy.booking.repository.ItemRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            Set<String> attributes = mapAttributes(entity.getAttributes());
            result.add(ItemDto.builder()
                    .name(entity.getName())
                    .price(entity.getPrice())
                    .description(entity.getDescription())
                    .attributes(attributes)
                    .build());
        }
        return result;
    }

    private Set<String> mapAttributes(Set<ItemAttributeEntity> itemAttributeEntities) {
        Set<String> result = new HashSet<>();
        for (ItemAttributeEntity attributeEntity : itemAttributeEntities) {
            result.add(attributeEntity.getAttributeName());
        }
        return result;
    }
}
