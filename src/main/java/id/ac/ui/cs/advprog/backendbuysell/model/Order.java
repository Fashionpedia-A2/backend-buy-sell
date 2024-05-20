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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @JsonProperty("updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Date updatedAt;

    @NotNull(message = "Status must not be empty")
    @Column(name = "status", nullable = false)
    private String status = OrderStatus.MENUNGGU_PEMBAYARAN.name();

    @Setter
    @NotNull(message = "Total price must not be empty")
    @JsonProperty("total_price")
    @Column(name = "totalPrice", nullable = false)
    private Long totalPrice;

    @JsonProperty("listings")
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private List<ListingInOrder> listingInOrders = new ArrayList<>();

    @Setter
    @NotNull(message = "Seller must not be empty")
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Setter
    @NotNull(message = "Buyer must not be empty")
    @JsonProperty("buyer_id")
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Setter
    @JsonProperty("payment_id")
    @Column(name = "payment_id")
    private Long paymentId;


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public void setStatus(String status) {
        this.status = (status != null) ? status.toUpperCase() : null;
    }

    public void calculateTotalPrice() {
        long totalPrice = 0L;
        for (ListingInOrder listingInOrder : this.listingInOrders) {
            totalPrice = Math.addExact(totalPrice, listingInOrder.getSubTotalPrice());
        }
        this.setTotalPrice(totalPrice);
    }

    public void setListingInOrders(List<ListingInOrder> listingInOrders) {
        this.listingInOrders = listingInOrders;
        calculateTotalPrice();
    }

    public void addListingInOrder(ListingInOrder listingInOrder) {
        this.listingInOrders.add(listingInOrder);
        calculateTotalPrice();
    }

    public void removeListingInOrder(ListingInOrder listingInOrder) {
        this.listingInOrders.remove(listingInOrder);
        calculateTotalPrice();
    }

    public Errors validate() {
        BindingResult bindingResult = new BeanPropertyBindingResult(this, "order");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Order>> violations = validator.validate(this);
        for (ConstraintViolation<Order> violation : violations) {
            bindingResult.addError(
                    new FieldError("order", violation.getPropertyPath().toString(), violation.getMessage()));
        }
        validateStatus(bindingResult);
        return bindingResult;
    }

    private void validateStatus(BindingResult validationResult) {
        try {
            if (this.status == null) return;
            OrderStatus.fromString(this.status);
        } catch (IllegalArgumentException e) {
            validationResult.addError(new FieldError("order", "status",
                                                     "Status must be one of the following value: " + OrderStatus.getString()));
        }
    }
}
