package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingCondition;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingStatus;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Set;

@Entity
@Table(name = "listing")
@Getter
@Setter
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("seller_id")
    @NotBlank(message = "Seller_id must not be blank")
    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @NotNull(message = "Stock must not be empty")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @NotNull(message = "Price must not be empty")
    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "status", nullable = false)
    private String status = ListingStatus.PENDING.getValue();

    @Column(name = "category")
    private String category;

    @JsonProperty("image_url")
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "size")
    private String size;

    @Column(name = "condition")
    private String condition = ListingCondition.NOT_SPECIFIED.getValue();

    @Column(name = "description")
    private String description;

    public Listing(String name, String sellerId, int stock, Long price) {
        setName(name);
        setSellerId(sellerId);
        setStock(stock);
        setPrice(price);
        setStatus(ListingStatus.PENDING.getValue());
    }

    public Listing() {
    }

    public void setStatus(String status) {
        if (status != null) this.status = status.toUpperCase();
    }

    public void setCondition(String condition) {
        if (condition != null) this.condition = condition.toUpperCase();
    }

    public Errors validate() {
        BindingResult bindingResult = new BeanPropertyBindingResult(this, "listing");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Listing>> violations = validator.validate(this);
        for (ConstraintViolation<Listing> violation : violations) {
            bindingResult.addError(new FieldError("listing", violation.getPropertyPath().toString(), violation.getMessage()));
        }

        validateStock(bindingResult);
        validatePrice(bindingResult);
        validateStatus(bindingResult);
        validateCondition(bindingResult);
        return bindingResult;
    }

    public void validateStock(BindingResult bindingResult) {
        if (stock != null && stock < 0) {
            bindingResult.addError(new FieldError("listing", "stock", "Stock must be a non-negative number"));
        }
    }

    public void validatePrice(BindingResult bindingResult) {
        if (price != null && price < 0L) {
            bindingResult.addError(new FieldError("listing", "price", "Price must be a non-negative number"));
        }
    }

    public void validateStatus(BindingResult bindingResult) {
        status = status.toUpperCase();
        if (!ListingStatus.contains(status)) {
            bindingResult.addError(new FieldError("listing", "status", "Status must be in " + ListingStatus.getString()));
        }
    }

    public void validateCondition(BindingResult bindingResult) {
        condition = condition.toUpperCase();
        if (!ListingCondition.contains(condition)) {
            bindingResult.addError(new FieldError("listing", "condition", "Condition must be in " + ListingCondition.getString()));
        }
    }


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
