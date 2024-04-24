package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingServiceImpl implements ListingService{
    public Listing create(Listing listing) {
        return null;
    }
    public List<Listing> getAll() {
        return null;
    }
    public Listing getById(Long id) {
        return null;
    }
    public Listing update(Long id, Listing updatedListing) {
        return null;
    }
    public Listing delete(Long id) {
        return null;
    }

    public List<Listing> getAllActiveListing() {
        return null;
    }
    public List<Listing> searchByName() {
        return null;
    }
    public List<Listing> getSellerListing() {
        return null;
    }
    public void setStatus(Long id, String status) {
    }
}
