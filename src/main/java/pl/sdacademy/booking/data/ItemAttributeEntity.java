package pl.sdacademy.booking.data;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_attribute")
@Data
public class ItemAttributeEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "attribute_name")
    private String attributeName;
}
