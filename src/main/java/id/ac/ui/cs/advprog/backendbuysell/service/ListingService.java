package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingSearchRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingSearchResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ListingService {
    Listing create(Listing listing);

    ListingSearchResponseDTO getAll(ListingSearchRequestDTO searchCriteriaDTO);

    Optional<Listing> getById(Long id);

    Listing update(Long id, Listing updatedListing);

    Listing delete(Long id);

    ListingSearchResponseDTO getActiveListings(ListingSearchRequestDTO searchCriteriaDTO);

    ListingSearchResponseDTO getSellerListings(String sellerId, ListingSearchRequestDTO searchCriteriaDTO);

    void setStatus(Long id, String status);
}
