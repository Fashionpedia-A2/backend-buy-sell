package id.ac.ui.cs.advprog.backendbuysell.utils;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingSearchRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ListingSearchQueryBuilder {
    public static Specification<Listing> buildSpecification(ListingSearchRequestDTO searchCriteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchCriteria.getName() != null) {
                predicates.add(builder.like(root.get("name"), "%" + searchCriteria.getName() + "%"));
            }

            if (searchCriteria.getMinPrice() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), searchCriteria.getMinPrice().toString()));
            }
            if (searchCriteria.getMaxPrice() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), searchCriteria.getMaxPrice().toString()));
            }
            if (searchCriteria.getConditions() != null) {
                List<Predicate> conditionPredicates = new ArrayList<>();
                for (String condition : searchCriteria.getConditions()) {
                    conditionPredicates.add(builder.like(builder.upper(root.get("condition")), condition.toUpperCase()));
                }
                predicates.add(builder.or(conditionPredicates.toArray(new Predicate[]{})));
            }

            if (searchCriteria.getStatuses() != null) {
                List<Predicate> statusPredicates = new ArrayList<>();
                for (String status : searchCriteria.getStatuses()) {
                    statusPredicates.add(builder.like(builder.upper(root.get("status")), status.toUpperCase()));
                }
                predicates.add(builder.or(statusPredicates.toArray(new Predicate[]{})));
            }

            if(searchCriteria.getSellerId() != null){
                predicates.add(builder.equal(root.get("sellerId"), searchCriteria.getSellerId()));
            }

            return builder.and(predicates.toArray(new Predicate[]{}));
        };
    }

    public static Sort buildSort(ListingSearchRequestDTO searchRequest) {
        String sortBy = searchRequest.getSortDirection();
        if (sortBy == null) {
            return Sort.unsorted();
        }
        Sort.Direction direction = (searchRequest.getSortDirection().equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC);
        return Sort.by(direction, sortBy);
    }
}
