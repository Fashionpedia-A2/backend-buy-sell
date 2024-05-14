package id.ac.ui.cs.advprog.backendbuysell.repository;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ListingRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ListingRepository repository;

    List<Listing> listings;

    @BeforeEach
    public void setUp(){
        this.listings = new ArrayList<>();
        Listing listing1 =  new Listing("Baju Koko Shimmer", "660bd2da-ed38-41c0-af02-797556f0b9a1", 100, 100_000L);
        Listing listing2 =  new Listing("Celana Shimmer", "660bd2da-ed38-41c0-af02-797556f0b9a1", 50, 50_000L);
        Listing listing3 =  new Listing("Baju Seragam SD", "3ec5d1a9-4387-4691-994d-dc6d48ce23e4", 50, 50_000L);

        this.listings.add(listing1);
        this.listings.add(listing2);
        this.listings.add(listing3);

        this.listings.forEach(entityManager::persist);
    }

    @AfterEach
    public void resetRepository(){
        this.entityManager.clear();
    }

    @Test
    void whenFindAllBySellerId_thenReturnListListing(){
        this.repository.saveAll(listings);

        String sellerId = listings.getFirst().getSellerId();
        List<Listing> sellerListings = this.repository.findAllBySellerId(sellerId);
        assertEquals(2, sellerListings.size());
    }


    @Test
    void whenFindAllByNonExistingSellerId_thenReturnEmptyList(){
        this.repository.saveAll(listings);

        String sellerId = "e69eb4a1-6f1f-43e6-8ea0-536346661926";
        List<Listing> sellerListings = this.repository.findAllBySellerId(sellerId);
        assertEquals(0, sellerListings.size());
    }

    @Test
    void whenFindAllByName_thenReturnListListing(){
        this.repository.saveAll(listings);

        String name = "baju";
        List<Listing> filteredListings = this.repository.findAllByName(name);
        assertEquals(2, filteredListings.size());
    }

    @Test
    void whenFindAllByNonMatchingName_thenReturnEmptyList(){
        this.repository.saveAll(listings);

        String name = "rok";
        List<Listing> filteredListings = this.repository.findAllByName(name);
        assertEquals(0, filteredListings.size());
    }
}
