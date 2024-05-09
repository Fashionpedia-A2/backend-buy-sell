package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingSearchCriteriaDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingServiceImpl implements ListingService {
    public Listing create(Listing listing) {
        return null;
    }

    public List<Listing> getAll(ListingSearchCriteriaDTO searchCriteriaDTO, Pageable pageable) {
        return null;
    }

    public Optional<Listing> getById(Long id) {
        return Optional.empty();
    }

    public Listing update(Long id, Listing updatedListing) {
        return null;
    }

    public Listing delete(Long id) {
        return null;
    }

    public List<Listing> getActiveListings(ListingSearchCriteriaDTO searchCriteriaDTO, Pageable pageable) {
        return null;
    }

    public List<Listing> getSellerListings(String sellerId, ListingSearchCriteriaDTO searchCriteriaDTO, Pageable pageable) {
        return null;
    }

    public void setStatus(Long id, String status) {
    }
}
