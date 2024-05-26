package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingStatus;
import id.ac.ui.cs.advprog.backendbuysell.exception.FieldValidationException;
import id.ac.ui.cs.advprog.backendbuysell.exception.ForbiddenException;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import id.ac.ui.cs.advprog.backendbuysell.utils.ListingSearchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    SellerService sellerService;

    public Listing create(Listing listing, Long sellerId) {
        listing.setSellerId(sellerId);
        Errors validationResult = listing.validate();
        if (validationResult.hasErrors()) {
            throw new FieldValidationException(validationResult.getAllErrors(), "Validation Error");
        }
        return listingRepository.save(listing);
    }

    public ListingListResponseDTO getAll(ListingListRequestDTO request) {
        Specification<Listing> specification = ListingSearchQueryBuilder.buildSpecification(request);
        Page<Listing> page = listingRepository.findAll(specification, request.getPageable());
        return ListingListResponseDTO
                .builder()
                .listings(page.getContent())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .build();
    }

    public Optional<Listing> getById(Long id) {
        return listingRepository.findById(id);
    }

    public Listing update(Long id, Listing updatedListing, Long sellerId) {
        Optional<Listing> oldResult = listingRepository.findById(id);
        if (oldResult.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (!isAuthenticated(oldResult.get(), sellerId)) {
            throw new ForbiddenException("User is not authorized to perform action on this listing");
        }
        updatedListing.setSellerId(sellerId);
        Errors validationResult = updatedListing.validate();
        if (validationResult.hasErrors()) {
            throw new FieldValidationException(validationResult.getAllErrors(), "Validation Error");
        }
        updatedListing.setId(id);
        return listingRepository.save(updatedListing);
    }

    public Listing delete(Long id, Long sellerId) {
        Listing listing = listingRepository.findById(id).orElseThrow();
        if (!isAuthenticated(listing, sellerId)) {
            throw new ForbiddenException("User is not authorized to perform action on this listing");
        }
        Errors validationResult = listing.validate();
        if(validationResult.hasErrors()){
            throw new FieldValidationException(validationResult.getAllErrors(), "Validation Error");
        }
        listingRepository.deleteById(id);
        return listing;
    }

    public ListingListResponseDTO getActiveListings(ListingListRequestDTO requestDTO) {
        List<String> status = new ArrayList<>();
        status.add(ListingStatus.ACTIVE.getValue());
        requestDTO.setStatus(status);
        return this.getAll(requestDTO);
    }

    public ListingListResponseDTO getSellerListings(Long sellerId, ListingListRequestDTO requestDTO) {
        requestDTO.setSellerId(sellerId);
        return this.getAll(requestDTO);
    }

    public void setStatus(Long id, String status, Long sellerId) {
        Listing listing = listingRepository.findById(id).orElseThrow();
        if (!isAuthenticated(listing, sellerId)) {
            throw new ForbiddenException("User is not authorized to perform action on this listing");
        }
        listing.setStatus(status);
        Errors validationResult = listing.validate();
        if(validationResult.hasErrors()){
            throw new FieldValidationException(validationResult.getAllErrors(), "Validation Error");
        }
        listingRepository.save(listing);
    }

    private boolean isAuthenticated(Listing listing, Long sellerId) {
        return listing.getSellerId().equals(sellerId);
    }
}
