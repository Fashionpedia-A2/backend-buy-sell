package id.ac.ui.cs.advprog.backendbuysell.dto;

import id.ac.ui.cs.advprog.backendbuysell.model.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ListingSearchRequestDTO {
    private String name;
    private Long minPrice;
    private Long maxPrice;
    private List<String> conditions;
    private List<String> statuses;
    private String sellerId;

    private String sortBy;
    private String sortDirection;

    private Integer pageNumber;
    private Integer itemsPerPage;
}
