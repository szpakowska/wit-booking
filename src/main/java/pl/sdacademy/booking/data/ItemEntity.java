package pl.sdacademy.booking.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@Data
public class ItemEntity {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private BigDecimal price;

    @ManyToMany
    @JoinTable(name = "item_to_attribute",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id"))
    private Set<ItemAttributeEntity> attributes = new HashSet<>();
}
