package id.ac.ui.cs.advprog.backendbuysell.model;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Set;

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
    @NotNull
    private Listing listing;

    @Column(name = "quantity", nullable = false)
    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @Min(value = 0)
    @Column(name = "sub_total_price", nullable = false)
    private Long subTotalPrice;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    public ListingInOrder() {
    }

    public ListingInOrder(Listing listing, Integer quantity) {
        this.listing = listing;
        this.quantity = quantity;
        this.calculateSubTotalPrice();
    }

    public void calculateSubTotalPrice() {
        assert this.listing != null && this.listing.getPrice() != null && this.quantity != null : "Product or quantity is missing for calculating subtotal price";
        this.subTotalPrice = Math.multiplyExact(this.listing.getPrice(), this.quantity);
    }

    public Errors validate() {
        BindingResult bindingResult = new BeanPropertyBindingResult(this, "listingInOrder");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ListingInOrder>> violations = validator.validate(this);
        for (ConstraintViolation<ListingInOrder> violation : violations) {
            bindingResult.addError(
                    new FieldError("listingInOrder", violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return bindingResult;
    }
}
