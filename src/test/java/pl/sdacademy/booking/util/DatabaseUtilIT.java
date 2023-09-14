package pl.sdacademy.booking.util;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.data.ItemEntity;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseUtilIT {

    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        entityManager = DatabaseUtil.getEntityManager();
    }

    @Test
    void shouldFindItemsInCatalog() {

        var query = entityManager.createQuery("select item from ItemEntity item "
                + " join item.attributes attr"
                + " where LOWER(attr.attributeName) in (:values)"
                + " order by item.id", ItemEntity.class);
        query.setParameter("values", Arrays.asList("twarz", "szyja"));

        var result = query.getResultList();

        assertThat(result).hasSize(4);
        // zadziala tylko dlatego, ze jest order by w zapytaniu
        // jakby order nie byl dodany kazde wywolanie mogloby sie skonczyc inna kolejnoscia zwracanych danych
        assertThat(result.get(0).getAttributes()).hasSize(1);
        assertThat(result.get(1).getPrice()).isEqualTo(BigDecimal.valueOf(450));
    }

    @Test
    void shouldFindAllItemsInCatalog() {

        var query = entityManager.createQuery("select item from ItemEntity item "
                + " order by item.id", ItemEntity.class);

        var result = query.getResultList();
        // jezeli z jakiegos powodu entity manages zostanie "w miedzyczasie" zamkniety pojawia sie problemy
//        entityManager.close();

        assertThat(result).hasSize(5);
        // zadziala tylko dlatego, ze jest order by w zapytaniu
        // jakby order nie byl dodany kazde wywolanie mogloby sie skonczyc inna kolejnoscia zwracanych danych
        assertThat(result.get(0).getAttributes()).hasSize(1);
        assertThat(result.get(1).getAttributes()).hasSize(1);
        assertThat(result.get(2).getAttributes()).hasSize(1);
        assertThat(result.get(2).getPrice()).isEqualTo(BigDecimal.valueOf(450));
    }
}