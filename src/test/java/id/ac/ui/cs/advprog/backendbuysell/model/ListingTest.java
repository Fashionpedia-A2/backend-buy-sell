package id.ac.ui.cs.advprog.backendbuysell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

public class ListingTest {
    Listing listing;
    Errors validationResult;

    Seller seller;

    @BeforeEach
    void setup() {
        seller = new Seller();
        seller.setId(1L);
        
        this.listing = new Listing();
        this.listing.setName("Baju Koko Shimmer");
        this.listing.setSeller(seller);
        this.listing.setStock(100);
        this.listing.setPrice(100_000L);
        this.listing.setCategory("Baju Muslim Pria");
        this.listing.setImageUrl("https://bajukokopria.com");
        this.listing.setSize("M");
        this.listing.setDescription("Lorem Ipsum");
        this.validationResult = new BeanPropertyBindingResult(this.listing, "listing");
    }

    @Test
    void testMinimumRequiredField(){
        Listing listing = new Listing();
        listing.setName("Baju Koko Shimmer");
        listing.setSeller(seller);
        listing.setStock(100);
        listing.setPrice(100_000L);
        validationResult = listing.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void testNullName(){
        listing.setName(null);
        validationResult = listing.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testBlankName(){
        listing.setName("   ");
        validationResult = listing.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testNullSeller(){
        listing.setSeller(null);
        validationResult = listing.validate();
        assertTrue(validationResult.hasErrors());
    }


    @Test
    void testNullStock(){
        listing.setStock(null);
        validationResult = listing.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testSetNonNegativeStock() {
        this.listing.setStock(0);
        validationResult = this.listing.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void testSetNegativeStock() {
        this.listing.setStock(-1);
        validationResult = this.listing.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testNullPrice(){
        listing.setPrice(null);
        validationResult = listing.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testSetNonNegativePrice() {
        this.listing.setPrice(0L);
        validationResult = this.listing.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void testSetNegativePrice() {
        this.listing.setPrice(-1L);
        validationResult = this.listing.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testSetValidStatusAllCapital(){
        this.listing.setStatus("VERIFIED");
        validationResult = this.listing.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void testSetValidStatusLowerCase(){
        this.listing.setStatus("PenDIng");
        validationResult = this.listing.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void testSetInvalidStatus() {
        this.listing.setStatus("Jomblo");
        validationResult = this.listing.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void testSetValidConditionAllCapital(){
        this.listing.setCondition("NEW");
        validationResult = this.listing.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void testSetValidConditionLowerCase(){
        this.listing.setCondition("sAtisFactoRY");
        validationResult = this.listing.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void testSetInvalidCondition() {
        this.listing.setCondition("Jelek");
        validationResult = this.listing.validate();
        assertTrue(validationResult.hasErrors());
    }
}
