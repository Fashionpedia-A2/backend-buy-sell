package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingCreationRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingServiceBuy {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public List<Listing> findAll(){
        return listingRepository.findAll();
    }

    public Listing create(ListingCreationRequestDTO l){
        Listing newListing = Listing.builder()
                .name(l.getName())
                .imageUrl(l.getImageUrl())
                .stock(l.getStock())
                .price(l.getPrice())
                .size(l.getSize())
                .condition(l.getCondition())
                .sellerId(l.getSeller_id())
                .description(l.getDescription())
                .build();


        try{
            return listingRepository.save(newListing);
        } catch (DataIntegrityViolationException e){
            return null;
        }
    }

    public Listing save(Listing listing){
        if (listingRepository.existsById(listing.getId())){
            try{
                return listingRepository.save(listing);
            } catch (DataIntegrityViolationException e){
                return null;
            }

        } else {
            return null;
        }
    }

    public void delete(Long id){
        listingRepository.deleteById(id);
    }

    public Optional<Listing> findById(Long id){
        return listingRepository.findById(id);
    }
}