package id.ac.ui.cs.advprog.backendbuysell.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@Builder
public class ListingListRequestDTO {
    private String name;
    private Long minPrice;
    private Long maxPrice;
    private List<String> conditions;
    private List<String> status;
    private Long sellerId;

    private Pageable pageable;
}
