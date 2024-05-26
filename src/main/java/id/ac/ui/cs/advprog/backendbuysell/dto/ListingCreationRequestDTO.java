package id.ac.ui.cs.advprog.backendbuysell.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingCondition;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingStatus;
import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingCreationRequestDTO {
    private Long id;
    private String name;
    private Long seller_id;
    private Integer stock;
    private Long price;
    private String category;
    private String imageUrl;
    private String size;
    private String condition = ListingCondition.NOT_SPECIFIED.getValue();
    private String description;
}
