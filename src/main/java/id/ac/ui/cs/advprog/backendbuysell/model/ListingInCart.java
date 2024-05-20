package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="listing_in_cart")
@Setter
@Getter
@IdClass(ListingInCartId.class)
public class ListingInCart {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "id")
    private Listing listing;

    @Id
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Override
    public String toString() {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
