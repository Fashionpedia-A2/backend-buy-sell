package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;

import java.util.List;

public interface ListingService {
    Listing create(Listing listing);
    List<Listing> getAll();
    Listing getById(Long id);
    Listing update(Long id, Listing updatedListing);
    Listing delete(Long id);

    List<Listing> getAllActiveListing();
    List<Listing> searchByName();
    List<Listing> getSellerListing();
    void setStatus(Long id, String status);
}
