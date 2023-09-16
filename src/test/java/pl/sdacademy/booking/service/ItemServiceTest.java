package pl.sdacademy.booking.service;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.data.ItemAttributeEntity;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.ItemDto;
import pl.sdacademy.booking.repository.ItemRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ItemServiceTest {

    private ItemService sut;
    @Test
    void shouldResultAllItemsInDbAsListOfDto() {
        sut = new ItemService(new TestItemRepository());

        List<ItemDto> result = sut.findItems();

        assertThat(result).hasSize(2);
        ItemDto first = result.get(0);
        // liczba ponizszych asercji sygnalizuje, ze klasa moze miec za duzo odpowiedzialnosci
        // powinna zostac podzielona na bardziej specjalistyczne klasy
        assertThat(first.getName()).isEqualTo("Pierwszy");
        assertThat(first.getDescription()).isEqualTo("Przykladowy opis");
        assertThat(first.getPrice()).isEqualTo(BigDecimal.valueOf(120.0));

        assertThat(first.getAttributes()).hasSize(1)
                .contains("twarz");

        ItemDto second = result.get(1);
        assertThat(second.getAttributes()).isEmpty();
    }

    public static class TestItemRepository implements ItemRepository {

        @Override
        public List<ItemEntity> findItems() {
            Set<ItemAttributeEntity> attributes = new HashSet<>();
            ItemAttributeEntity firstAttribute = new ItemAttributeEntity();
            firstAttribute.setId(1L);
            firstAttribute.setAttributeName("twarz");
            attributes.add(firstAttribute);
            ItemEntity first = new ItemEntity();
            first.setId(1l);
            first.setName("Pierwszy");
            first.setDescription("Przykladowy opis");
            first.setPrice(BigDecimal.valueOf(120.0));
            first.setAttributes(attributes);
            ItemEntity second = new ItemEntity();
            second.setId(2l);
            second.setName("Drugi");
            second.setDescription("drugi opis");
            second.setAttributes(new HashSet<>());
            first.setPrice(BigDecimal.valueOf(120.0));
            return List.of(first, second);
        }

        @Override
        public void addItem(ItemEntity item) {

        }

        @Override
        public Long findItemByName(String name) {
            return null;
        }
    }
}