package id.ac.ui.cs.advprog.backendbuysell;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ListingTest {
    Listing listing;
    @BeforeEach
    void setUp(){
        this.listing = new Listing();
        this.listing.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.listing.setName("Baju Sefriano");
    }
    @Test
    void testGetProductId(){
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.listing.getId());
    }
    @Test
    void testGetProductName() {
        assertEquals("Baju Sefriano", this.listing.getName());
    }

}
