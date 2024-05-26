package id.ac.ui.cs.advprog.backendbuysell.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateListingInCartRequestDTO {
    private Long listingId;
    private int quantity;
}
