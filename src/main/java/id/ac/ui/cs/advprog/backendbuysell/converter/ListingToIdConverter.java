package id.ac.ui.cs.advprog.backendbuysell.converter;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ListingToIdConverter implements AttributeConverter<Listing, Long> {
    @Autowired
    ListingRepository listingRepository;

    @Override
    public Long convertToDatabaseColumn(Listing listing) {
        return listing.getId();
    }

    @Override
    public Listing convertToEntityAttribute(Long listingId) {
        return listingRepository.findById(listingId).orElseThrow(
                () -> new NoSuchElementException("Listing with ID " + listingId + " not found"));
    }
}
