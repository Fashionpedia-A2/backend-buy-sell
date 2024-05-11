package id.ac.ui.cs.advprog.backendbuysell.utils;

import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSearchQueryBuilder {
    public static Specification<Order> buildSpecification(OrderListRequestDTO searchCriteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchCriteria.getStatuses() != null) {
                List<Predicate> statusPredicates = new ArrayList<>();
                for (String status : searchCriteria.getStatuses()) {
                    statusPredicates.add(builder.equal(builder.upper(root.get("status")), status.toUpperCase()));
                }
                predicates.add(builder.or(statusPredicates.toArray(new Predicate[]{})));
            }

            if (searchCriteria.getSellerId() != null) {
                predicates.add(builder.equal(root.get("sellerId"),  searchCriteria.getSellerId() ));
            }

            if (searchCriteria.getBuyerId() != null) {
                predicates.add(builder.equal(root.get("buyerId"),  searchCriteria.getBuyerId() ));
            }

            if (searchCriteria.getPaymentId() != null) {
                predicates.add(builder.equal(root.get("paymentId"),  searchCriteria.getPaymentId() ));
            }

            if (searchCriteria.getCreatedAtStart() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), searchCriteria.getCreatedAtStart().toString()));
            }
            if (searchCriteria.getCreatedAtEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), searchCriteria.getCreatedAtEnd().toString()));
            }

            return builder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
