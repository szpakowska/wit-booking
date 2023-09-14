package pl.sdacademy.booking.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "booking")
@Data
public class BookingEntity {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;
    // in final solution it would be rather another entity which represents customer
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_contact")
    private String customerContact;
}
