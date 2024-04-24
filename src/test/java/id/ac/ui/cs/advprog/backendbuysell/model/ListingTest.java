package id.ac.ui.cs.advprog.backendbuysell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListingTest {
    Listing listing;

    @BeforeEach
    void setup() {
        this.listing = new Listing();
        this.listing.setId("3cf20657-5575-442e-b102-a96021a3112b");
        this.listing.setName("Baju Koko Shimmer");
        this.listing.setSellerId("660bd2da-ed38-41c0-af02-797556f0b9a1");
        this.listing.setCategory("Baju Muslim Pria");
        this.listing.setImageUrl("https://bajukokopria.com");
        this.listing.setSize("M");
        this.listing.setCondition("new");
        this.listing.setStatus("verified");
        this.listing.setDescription("Lorem Ipsum");
    }

    @Test
    void testSetNonNegativeStock() {
        this.listing.setStock(0);
        assertEquals(0, this.listing.getStock(0));
    }

    @Test
    void testSetNegativeStock(){
        assertThrows(IllegalArgumentException.class, () -> {
            this.listing.setStock(-1);
        });
    }

    @Test
    void testSetNonNegativePrice() {
        this.listing.setPrice(0);
        assertEquals(0, this.listing.getPrice(0));
    }

    @Test
    void testSetNegativeStock(){
        assertThrows(IllegalArgumentException.class, () -> {
            this.listing.setPrice(-1);
        });
    }
}
