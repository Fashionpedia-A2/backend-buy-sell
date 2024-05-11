package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import id.ac.ui.cs.advprog.backendbuysell.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name = "Order")
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Date updatedAt;

    @Column(name = "status", nullable = false)
    private String status = OrderStatus.MENUNGGU_PEMBAYARAN.name();

    @Column(name = "totalPrice", nullable = false)
    private Long totalPrice;

    @JsonProperty("listings")
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private List<ListingInOrder> listingInOrders = new ArrayList<>();

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "buyer_id", nullable = false)
    private String buyerId;

    @Column(name = "payment_id")
    private Long paymentId;


    public void calculateTotalPrice() {
    }

    public void setListingInOrders(List<ListingInOrder> listingInOrders) {
    }

    public void addListingInOrder(ListingInOrder listingInOrder) {
    }

    public void removeListingInOrder(ListingInOrder listingInOrder) {
    }

    public Errors validate() {
        BindingResult bindingResult = new BeanPropertyBindingResult(this, "order");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(this);
        for (ConstraintViolation<Order> violation : violations) {
            bindingResult.addError(
                    new FieldError("order", violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return bindingResult;
    }
}
