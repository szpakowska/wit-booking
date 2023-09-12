package pl.sdacademy.booking.data;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "event")
@Data
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;
    private LocalDateTime from;
    private LocalDateTime to;
}
