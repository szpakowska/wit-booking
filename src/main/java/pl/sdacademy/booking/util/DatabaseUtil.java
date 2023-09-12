package pl.sdacademy.booking.util;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class DatabaseUtil {
    private static EntityManager entityManager;

    private static void buildSessionFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BookingService");
        entityManager = emf.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            buildSessionFactory();
        }
        return entityManager;
    }
}
