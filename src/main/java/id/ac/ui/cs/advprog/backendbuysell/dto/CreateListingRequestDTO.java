package id.ac.ui.cs.advprog.backendbuysell.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateListingRequestDTO {
    private int userId;
    private Long listingId;
}
