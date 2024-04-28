package id.ac.ui.cs.advprog.backendbuysell;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@ActiveProfiles("test")
class ListingTest {
    Listing listing;
    @BeforeEach
    void setUp(){
        this.listing = new Listing();
        this.listing.setStock(10);
        this.listing.setName("Baju Sefriano");
    }
    @Test
    void testGetListingStock(){
        assertEquals(10, this.listing.getStock());
    }
    @Test
    void testGetListingName() {
        assertEquals("Baju Sefriano", this.listing.getName());
    }

}
