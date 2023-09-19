package pl.sdacademy.booking.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import pl.sdacademy.booking.data.EventEntity;
import pl.sdacademy.booking.util.DatabaseUtil;

import java.time.LocalDateTime;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    private EntityManager entityManager;

    public EventRepositoryImpl() {

        entityManager = DatabaseUtil.getEntityManager();
    }

    @Override
    public List<EventEntity> findEvents() {
        TypedQuery<EventEntity> query = entityManager
                .createQuery("SELECT event FROM EventEntity event", EventEntity.class);
        return query.getResultList();
    }

    @Override
    public void addEvent(EventEntity event) {
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
    }

    @Override
    public Long findEventsByDate(LocalDateTime date) {
        TypedQuery<Long> query = entityManager
                .createQuery("SELECT * FROM EventEntity event where time_from<=:dateParam and time_to>=:dateParam"
                        , Long.class);
        query.setParameter("dateParam", date);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return -1L; //bo nie będzie identyfikatorem, ale nie będzie też nullem
        }
    }


}
