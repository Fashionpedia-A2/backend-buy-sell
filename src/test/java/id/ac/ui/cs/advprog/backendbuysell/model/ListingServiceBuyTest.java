package id.ac.ui.cs.advprog.backendbuysell.model;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingCreationRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.repository.SellerRepository;
import id.ac.ui.cs.advprog.backendbuysell.service.ListingServiceBuy;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ListingServiceBuyTest {
    @Mock
    ListingRepository listingRepository;
    @Mock
    SellerRepository sellerRepository;
    @InjectMocks
    ListingServiceBuy listingServiceBuy;
    @BeforeEach
    public void setup(){
        initMocks(this);
    }
    private List<Listing> createSampleListings() {
        List<Listing> listings = new ArrayList<>();

        // Create a few sample listings with different properties
        listings.add(new Listing(
                "Sample Listing 1",
                "https://example.com/image1.jpg",
                5,
                20000L,
                "Electronics",
                "M",
                "New",
                null, // Seller can be set separately in the test if needed
                "A detailed description of Sample Listing 1"
        ));

        listings.add(new Listing(
                "Sample Listing 2",
                "https://example.com/image2.jpg",
                10,
                50000L,
                "Fashion",
                "L",
                "Used",
                null, // Seller can be set separately in the test if needed
                "Another detailed description for Sample Listing 2"
        ));

        // You can add more listings as needed

        return listings;
    }
    @Test
    public void testFindAll_EmptyList() {
        // Mock listingRepository to return an empty list
        when(listingRepository.findAll()).thenReturn(Collections.emptyList());
        List<Listing> allListings = listingServiceBuy.findAll();

        // Assert that an empty list is returned
        assertEquals(Collections.emptyList(), allListings);
    }

    @Test
    public void testFindAll_PopulatedList() {
        // Mock listingRepository to return a list of listings
        List<Listing> expectedListings = createSampleListings(); // Create some sample listings

        when(listingRepository.findAll()).thenReturn(expectedListings);

        List<Listing> allListings = listingServiceBuy.findAll();

        // Assert that the returned list matches the expected list
        assertEquals(expectedListings, allListings);
    }

    @Test
    public void testCreate_ValidListingAndSeller() {
        ListingCreationRequestDTO request = new ListingCreationRequestDTO(
                null, // id will be set to null
                "Sample Listing",
                13L,
                5,
                20000L,
                "Electronics",
                "M",
                "New",
                "baru",
                "Description"
        );

        Seller seller = new Seller(); // Create a dummy seller
        seller.setId(13L);
        when(sellerRepository.getReferenceById(request.getSeller_id())).thenReturn(seller);
        when(listingRepository.save(any(Listing.class))).thenReturn(new Listing()); // Assuming successful save

        Listing createdListing = listingServiceBuy.create(request);

        // Assertions remain mostly the same (check for successful listing creation)
        assertNotNull(createdListing);
        // ... other assertions
    }
}
