package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingSearchCriteriaDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ListingService {
    Listing create(Listing listing);

    List<Listing> getAll(ListingSearchCriteriaDTO searchCriteriaDTO, Pageable pageable);

    Optional<Listing> getById(Long id);

    Listing update(Long id, Listing updatedListing);

    Listing delete(Long id);

    List<Listing> getActiveListings(ListingSearchCriteriaDTO searchCriteriaDTO, Pageable pageable);

    List<Listing> getSellerListings(String sellerId, ListingSearchCriteriaDTO searchCriteriaDTO, Pageable pageable);

    void setStatus(Long id, String status);
}
