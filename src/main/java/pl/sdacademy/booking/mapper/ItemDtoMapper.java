package pl.sdacademy.booking.mapper;

import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.ItemDto;

import pl.sdacademy.booking.data.ItemAttributeEntity;

import java.util.HashSet;
import java.util.Set;

public class ItemDtoMapper {
    public static ItemDto map(ItemEntity entity) {
        Set<String> attributes = mapAttributes(entity.getAttributes());
        return ItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .attributes(attributes)
                .build();
    }

    private static Set<String> mapAttributes(Set<ItemAttributeEntity> itemAttributeEntities) {
        Set<String> result = new HashSet<>();
        for (ItemAttributeEntity attributeEntity : itemAttributeEntities) {
            result.add(attributeEntity.getAttributeName());
        }
        return result;
    }
}
