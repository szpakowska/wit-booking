package pl.sdacademy.booking.data;

import jakarta.persistence.*;
import lombok.Data;

//@Entity
//@Table(name = "booking")
@Data
public class Booking {
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
