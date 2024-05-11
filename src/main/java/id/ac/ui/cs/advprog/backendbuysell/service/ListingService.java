package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;


import java.util.Optional;

public interface ListingService {
    Listing create(Listing listing, String sellerId);

    ListingListResponseDTO getAll(ListingListRequestDTO searchCriteriaDTO);

    Optional<Listing> getById(Long id);

    Listing update(Long id, Listing updatedListing, String sellerId);

    Listing delete(Long id, String sellerId);

    void setStatus(Long id, String status, String sellerId);

    ListingListResponseDTO getActiveListings(ListingListRequestDTO searchCriteriaDTO);

    ListingListResponseDTO getSellerListings(String sellerId, ListingListRequestDTO searchCriteriaDTO);

}
