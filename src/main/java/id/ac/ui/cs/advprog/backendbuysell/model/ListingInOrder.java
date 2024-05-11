package id.ac.ui.cs.advprog.backendbuysell.model;

import jakarta.persistence.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Entity(name = "ListingInOrder")
@Table(name = "listing_in_order")
@Getter
@Setter
public class ListingInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "sub_total_price", nullable = false)
    private Long subTotalPrice;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    public ListingInOrder() {
    }

    public ListingInOrder(Listing listing, Integer quantity) {
    }

    public void calculateSubTotalPrice(){}

    public Errors validate(){
        BindingResult bindingResult = new BeanPropertyBindingResult(this, "listing");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return bindingResult;
    }
}
