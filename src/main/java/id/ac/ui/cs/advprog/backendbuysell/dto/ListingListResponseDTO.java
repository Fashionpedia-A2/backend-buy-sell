package id.ac.ui.cs.advprog.backendbuysell.dto;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ListingListResponseDTO {
    private List<Listing> listings;
    private Integer currentPage;
    private Long totalItems;
    private Integer totalPages;
}
