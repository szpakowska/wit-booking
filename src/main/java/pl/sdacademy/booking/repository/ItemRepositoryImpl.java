package pl.sdacademy.booking.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.util.DatabaseUtil;

import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    private EntityManager entityManager;

    public ItemRepositoryImpl() {
        entityManager = DatabaseUtil.getEntityManager();
    }

    @Override
    public List<ItemEntity> findItems() {
        TypedQuery<ItemEntity> items = entityManager
                .createQuery("select item from ItemEntity item "
                        , ItemEntity.class);
        return items.getResultList();
    }

    @Override
    public void addItem(ItemEntity item) {

    }

    @Override
    public Long findItemByName(String name) {
        return null;
    }
}
