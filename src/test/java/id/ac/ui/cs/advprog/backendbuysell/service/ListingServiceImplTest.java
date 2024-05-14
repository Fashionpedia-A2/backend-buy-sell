package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingCondition;
import id.ac.ui.cs.advprog.backendbuysell.enums.ListingStatus;
import id.ac.ui.cs.advprog.backendbuysell.exception.FieldValidationException;
import id.ac.ui.cs.advprog.backendbuysell.exception.ForbiddenException;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ListingServiceImplTest {

    @Mock
    ListingRepository repository;

    @InjectMocks
    ListingServiceImpl service;

    List<Listing> listings;
    String sellerId;
    ListingListRequestDTO searchCriteriaDTO = ListingListRequestDTO.builder().pageable(PageRequest.of(0, 20)).build();

    @BeforeEach
    public void setUp() {
        Listing listing1 = new Listing("Baju Koko Shimmer", "660bd2da-ed38-41c0-af02-797556f0b9a1", 100, 100_000L);
        listing1.setId(1L);
        listing1.setCategory("Baju Muslim Pria");
        listing1.setImageUrl("https://bajukokopria.com");
        listing1.setSize("M");
        listing1.setCondition(ListingCondition.NEW.getValue());
        listing1.setStatus(ListingStatus.VERIFIED.getValue());
        listing1.setDescription("Lorem Ipsum");

        Listing listing2 = new Listing("Rok Standard SD", "660bd2da-ed38-41c0-af02-797556f0b9a1", 10, 150_000L);
        listing2.setId(2L);
        listing2.setCategory("Seragam SD");
        listing2.setImageUrl("https://roksd.com");
        listing2.setSize("M");
        listing2.setCondition(ListingCondition.SATISFACTORY.getValue());
        listing2.setStatus(ListingStatus.PENDING.getValue());
        listing2.setDescription("Lorem Ipsum");

        Listing listing3 = new Listing("Topi Channel", "931bd2da-ed38-41c0-af02-797556f0b9a1", 10, 150_000L);
        listing3.setId(3L);
        listing3.setCategory("Topi");
        listing3.setImageUrl("https://topichannel.com");
        listing3.setCondition(ListingCondition.VERY_GOOD.getValue());
        listing3.setStatus(ListingStatus.VERIFIED.getValue());
        listing3.setDescription("Lorem Ipsum");

        this.listings = new ArrayList<>();
        this.listings.add(listing1);
        this.listings.add(listing2);
        this.listings.add(listing3);

        this.sellerId = listing1.getSellerId();
    }


    @Test
    void testCreateListing() {
        Listing listing = this.listings.getFirst();
        doReturn(listing).when(repository).save(any(Listing.class));

        Listing savedListing = service.create(listing, this.sellerId);
        verify(repository, times(1)).save(any(Listing.class));
        assertEquals(listing.getId(), savedListing.getId());
    }


    @Test
    void testGetAllListings() {
        Page<Listing> page = new PageImpl<>(listings);
        doReturn(page).when(repository).findAll(ArgumentMatchers.<Specification<Listing>>any(), any(Pageable.class));

        List<Listing> savedListings = service.getAll(searchCriteriaDTO).getListings();

        assertEquals(this.listings.size(), savedListings.size());
    }

    @Test
    void testGetById() {
        Listing listing = this.listings.getFirst();
        doReturn(Optional.of(listing)).when(repository).findById(listing.getId());

        Optional<Listing> result = service.getById(listing.getId());

        verify(repository, times(1)).findById(any(Long.class));
        assertTrue(result.isPresent());
        assertEquals(listing.getId(), result.get().getId());
    }

    @Test
    void testGetByIdIfIdNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any(Long.class));

        Optional<Listing> result = service.getById(666L);

        verify(repository, times(1)).findById(any(Long.class));
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateListing() {
        Listing original = this.listings.getFirst();
        Listing updatedRequest = new Listing("Baju-bajuan", original.getSellerId(), 0, original.getPrice());
        updatedRequest.setCategory(original.getCategory());
        updatedRequest.setImageUrl(original.getImageUrl());
        updatedRequest.setSize(original.getSize());
        updatedRequest.setCondition(ListingCondition.NEW.getValue());
        updatedRequest.setStatus(original.getStatus());
        updatedRequest.setDescription(original.getDescription());

        doReturn(Optional.of(original)).when(repository).findById(any(Long.class));
        doReturn(updatedRequest).when(repository).save(any(Listing.class));

        Listing updatedResult = service.update(original.getId(), updatedRequest, updatedRequest.getSellerId());

        verify(repository, times(1)).findById(any(Long.class));
        verify(repository, times(1)).save(any(Listing.class));

        assertEquals(original.getId(), updatedResult.getId());
        assertEquals(updatedRequest.getName(), updatedResult.getName());
        assertEquals(updatedRequest.getStock(), updatedResult.getStock());
        assertEquals(updatedRequest.getCondition(), updatedResult.getCondition());
    }

    @Test
    void testUpdateListingIfIdNotFound() {
        Listing listing = this.listings.getFirst();
        doReturn(Optional.empty()).when(repository).findById(any(Long.class));
        assertThrows(NoSuchElementException.class, () -> service.update(listing.getId(), listing, this.sellerId));
    }

    @Test
    void testUpdateListingByUnauthorizedUser(){
        Listing listing = this.listings.getFirst();
        doReturn(Optional.of(listing)).when(repository).findById(any(Long.class));
        assertThrows(ForbiddenException.class, () -> service.update(listing.getId(), listing, "hacker"));
        verify(repository, times(0)).save(any(Listing.class));
    }

    @Test
    void testDeleteListing() {
        Listing listing = this.listings.getFirst();
        System.out.println(listing.getId());
        doReturn(Optional.of(listing)).when(repository).findById(any(Long.class));

        Listing deletedListing = service.delete(listing.getId(), this.sellerId);

        verify(repository, times(1)).deleteById(any(Long.class));
        assertEquals(listing.getId(), deletedListing.getId());
    }

    @Test
    void testDeleteListingIfIdNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any(Long.class));
        assertThrows(NoSuchElementException.class, () -> service.delete(666L, this.sellerId));
    }

    @Test
    void testDeleteListingByUnauthorizedUser(){
        Listing listing = this.listings.getFirst();
        doReturn(Optional.of(listing)).when(repository).findById(any(Long.class));
        assertThrows(ForbiddenException.class, () -> service.delete(listing.getId(), "hacker"));
        verify(repository, times(0)).deleteById(any(Long.class));
    }

    @Test
    void testGetAllActiveListing() {
        List<Listing> activeListings = new ArrayList<>();
        for (Listing listing : this.listings) {
            if (ListingStatus.VERIFIED.getValue().equals(listing.getStatus())) {
                activeListings.add(listing);
            }
        }
        Page<Listing> page = new PageImpl<>(activeListings);
        doReturn(page).when(repository).findAll(ArgumentMatchers.<Specification<Listing>>any(), any(Pageable.class));

        List<Listing> result = service.getActiveListings(this.searchCriteriaDTO).getListings();

        assertEquals(activeListings.size(), result.size());
    }

    @Test
    void testGetSellerListings() {
        String sellerId = this.listings.getFirst().getSellerId();
        List<Listing> sellerListings = new ArrayList<>();
        for (Listing listing : this.listings) {
            if (listing.getSellerId().equals(sellerId)) {
                sellerListings.add(listing);
            }
        }
        Page<Listing> page = new PageImpl<>(sellerListings);
        doReturn(page).when(repository).findAll(ArgumentMatchers.<Specification<Listing>>any(), any(Pageable.class));
        List<Listing> result = service.getSellerListings(sellerId, this.searchCriteriaDTO).getListings();

        assertEquals(sellerListings.size(), result.size());
    }

    @Test
    void testSetListingStatus() {
        Listing listing = this.listings.getFirst();
        listing.setStatus(ListingStatus.REJECTED.getValue());
        doReturn(Optional.of(listing)).when(repository).findById(any(Long.class));
        doReturn(listing).when(repository).save(any(Listing.class));

        service.setStatus(listing.getId(), ListingStatus.REJECTED.getValue(), this.sellerId);
        verify(repository, times(1)).save(any(Listing.class));

        Listing result = service.getById(listing.getId()).orElseThrow();
        assertEquals(ListingStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testSetListingStatusIfListingIdNotFound() {
        doReturn(Optional.empty()).when(repository).findById(any(Long.class));

        assertThrows(NoSuchElementException.class, () -> {
            service.setStatus(123L, ListingStatus.REJECTED.getValue(), this.sellerId);
        });

        verify(repository, times(0)).save(any(Listing.class));
    }

    @Test
    void testSetListingStatusWithInvalidStatus() {
        Listing listing = this.listings.getFirst();
        doReturn(Optional.of(listing)).when(repository).findById(any(Long.class));

        assertThrows(FieldValidationException.class, () -> {
            service.setStatus(listing.getId(), "DUMMY", this.sellerId);
        });
        verify(repository, times(0)).save(any(Listing.class));
    }

    @Test
    void testSetListingStatusByUnauthorizedUser(){
        Listing listing = this.listings.getFirst();
        doReturn(Optional.of(listing)).when(repository).findById(any(Long.class));

        assertThrows(ForbiddenException.class, () -> {
            service.setStatus(listing.getId(), ListingStatus.REJECTED.getValue(), "hacker");
        });
        verify(repository, times(0)).deleteById(any(Long.class));
    }
}
