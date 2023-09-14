package pl.sdacademy.booking.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ItemDto {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;

    private Set<String> attributes;
}
