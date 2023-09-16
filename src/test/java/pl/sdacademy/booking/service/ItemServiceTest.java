package pl.sdacademy.booking.service;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.ItemDto;
import pl.sdacademy.booking.repository.ItemRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemServiceTest {

    private ItemService sut;
    @Test
    void shouldResultAllItemsInDbAsListOfDto() {
        sut = new ItemService(new TestItemRepository());

        List<ItemDto> result = sut.findItems();

        assertThat(result).hasSize(2);
        ItemDto first = result.get(0);
        assertThat(first.getName()).isEqualTo("Pierwszy");
        assertThat(first.getDescription()).isEqualTo("Przykladowy opis");
        assertThat(first.getPrice()).isEqualTo(BigDecimal.valueOf(120.0));

    }

    public static class TestItemRepository implements ItemRepository {

        @Override
        public List<ItemEntity> findItems() {
            ItemEntity first = new ItemEntity();
            first.setId(1l);
            first.setName("Pierwszy");
            first.setDescription("Przykladowy opis");
            first.setPrice(BigDecimal.valueOf(120.0));
            ItemEntity second = new ItemEntity();
            second.setId(2l);
            second.setName("Drugi");
            second.setDescription("drugi opis");
            first.setPrice(BigDecimal.valueOf(120.0));
            return List.of(first, second);
        }
    }
}