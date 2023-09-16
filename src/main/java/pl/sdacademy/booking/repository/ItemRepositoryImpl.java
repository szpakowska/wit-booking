package pl.sdacademy.booking.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
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
                .createQuery("select item from ItemEntity item ", ItemEntity.class);
        return items.getResultList();
    }

    @Override
    public void addItem(ItemEntity item) {
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }

    @Override
    public Long findItemByName(String searchedName) {

        TypedQuery<Long> items = entityManager
                .createQuery("select id from ItemEntity item where searchedName=:nameParam ", Long.class);
        items.setParameter("nameParam", searchedName);
        try {
            return items.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return -1L;
        }
    }
}
