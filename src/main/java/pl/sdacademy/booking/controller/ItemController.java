package pl.sdacademy.booking.controller;

import pl.sdacademy.booking.repository.ItemRepositoryImpl;
import pl.sdacademy.booking.service.ItemService;

public class ItemController {

    private final ItemService itemService;

    public ItemController() {
        // nie najlepsze rozwiazanie - skrot by moc zaprezentowac pseudo controller
        this.itemService = new ItemService(new ItemRepositoryImpl());
    }

    public void presentCatalog() {
        System.out.println("\n KATALOG ");
        itemService.findItems()
                .forEach(itemDto -> System.out.println(itemDto));
        System.out.println("\n KONIEC KATALOGU ");
    }
}
