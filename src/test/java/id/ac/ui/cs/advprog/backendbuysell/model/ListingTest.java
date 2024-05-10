package id.ac.ui.cs.advprog.backendbuysell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

public class ListingTest {
    Listing listing;
    BindingResult bindingResult;

    @BeforeEach
    void setup() {
        this.listing = new Listing("Baju Koko Shimmer", "660bd2da-ed38-41c0-af02-797556f0b9a1", 100, 100_000L);
        this.listing.setCategory("Baju Muslim Pria");
        this.listing.setImageUrl("https://bajukokopria.com");
        this.listing.setSize("M");
        this.listing.setDescription("Lorem Ipsum");
        this.bindingResult = new BeanPropertyBindingResult(this.listing, "listing");
    }

    @Test
    void testMinimumRequiredField(){
        Listing listing = new Listing("Baju Koko Shimmer", "660bd2da-ed38-41c0-af02-797556f0b9a1", 100, 100_000L);;
        listing.validate(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void testNullName(){
        listing.setName(null);
        listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testBlankName(){
        listing.setName("   ");
        listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testNullSellerId(){
        listing.setSellerId(null);
        listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testBlankSellerId(){
        listing.setSellerId("   ");
        listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testNullStock(){
        listing.setStock(null);
        listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testSetNonNegativeStock() {
        this.listing.setStock(0);
        this.listing.validate(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void testSetNegativeStock() {
        this.listing.setStock(-1);
        this.listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testNullPrice(){
        listing.setPrice(null);
        listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testSetNonNegativePrice() {
        this.listing.setPrice(0L);
        this.listing.validate(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void testSetNegativePrice() {
        this.listing.setPrice(-1L);
        this.listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testSetValidStatusAllCapital(){
        this.listing.setStatus("VERIFIED");
        this.listing.validate(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void testSetValidStatusLowerCase(){
        this.listing.setStatus("PenDIng");
        this.listing.validate(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void testSetInvalidStatus() {
        this.listing.setStatus("Jomblo");
        this.listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    void testSetValidConditionAllCapital(){
        this.listing.setCondition("NEW");
        this.listing.validate(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void testSetValidConditionLowerCase(){
        this.listing.setCondition("sAtisFactoRY");
        this.listing.validate(bindingResult);
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void testSetInvalidCondition() {
        this.listing.setCondition("Jelek");
        this.listing.validate(bindingResult);
        assertTrue(bindingResult.hasErrors());
    }
}
