package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingSearchRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingSearchResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingStatus;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import id.ac.ui.cs.advprog.backendbuysell.utils.ListingSearchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    ListingRepository listingRepository;

    public Listing create(Listing listing) {
        if (listingRepository.existsById(listing.getId())) {
            throw new DataIntegrityViolationException("");
        }
        return listingRepository.save(listing);
    }

    public ListingSearchResponseDTO getAll(ListingSearchRequestDTO request) {
        Specification<Listing> specification = ListingSearchQueryBuilder.buildSpecification(request);
        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getItemsPerPage(),
                ListingSearchQueryBuilder.buildSort(request)
        );
        Page<Listing> page = listingRepository.findAll(specification, pageable);
        return ListingSearchResponseDTO
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

    public Listing update(Long id, Listing updatedListing) {
        if (!listingRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        updatedListing.setId(id);
        return listingRepository.save(updatedListing);
    }

    public Listing delete(Long id) {
        Listing listing = listingRepository.findById(id).orElseThrow();
        listingRepository.deleteById(id);
        return listing;
    }

    public ListingSearchResponseDTO getActiveListings(ListingSearchRequestDTO requestDTO) {
        List<String> status = new ArrayList<>();
        status.add(ListingStatus.VERIFIED.getValue());
        requestDTO.setStatuses(status);
        return this.getAll(requestDTO);
    }

    public ListingSearchResponseDTO getSellerListings(String sellerId, ListingSearchRequestDTO requestDTO) {
        requestDTO.setSellerId(sellerId);
        return this.getAll(requestDTO);
    }

    public void setStatus(Long id, String status) {
        Listing listing = listingRepository.findById(id).orElseThrow();
        listing.setStatus(status);
        listingRepository.save(listing);
    }
}
