package id.ac.ui.cs.advprog.backendbuysell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListingTest {
    Listing listing;

    @BeforeEach
    void setup() {
        this.listing = new Listing("Baju Koko Shimmer", "660bd2da-ed38-41c0-af02-797556f0b9a1", 100, 100_000L);
        this.listing.setCategory("Baju Muslim Pria");
        this.listing.setImageUrl("https://bajukokopria.com");
        this.listing.setSize("M");
//        this.listing.setCondition("new");
//        this.listing.setStatus("verified");
        this.listing.setDescription("Lorem Ipsum");
    }

    @Test
    void testSetNonNegativeStock() {
        this.listing.setStock(0);
        assertEquals(0, this.listing.getStock());
    }

    @Test
    void testSetNegativeStock() {
        assertThrows(IllegalArgumentException.class, () -> this.listing.setStock(-1));
    }

    @Test
    void testSetNonNegativePrice() {
        this.listing.setPrice(0L);
        assertEquals(0L, this.listing.getPrice());
    }

    @Test
    void testSetNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> this.listing.setPrice(-1L));
    }

    @Test
    void testSetValidStatusAllCapital(){
        this.listing.setStatus("VERIFIED");
        assertEquals("VERIFIED", this.listing.getStatus());
    }

    @Test
    void testSetValidStatusLowerCase(){
        this.listing.setStatus("PenDIng");
        assertEquals("PENDING", this.listing.getStatus());
    }

    @Test
    void testSetInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> this.listing.setStatus("Jomblo"));
    }

    @Test
    void testSetValidConditionAllCapital(){
        this.listing.setCondition("NEW");
        assertEquals("NEW", this.listing.getCondition());
    }

    @Test
    void testSetValidConditionLowerCase(){
        this.listing.setCondition("sAtisFactoRY");
        assertEquals("SATISFACTORY", this.listing.getCondition());
    }

    @Test
    void testSetInvalidCondition() {
        assertThrows(IllegalArgumentException.class, () -> this.listing.setCondition("Jelek"));
    }
}
