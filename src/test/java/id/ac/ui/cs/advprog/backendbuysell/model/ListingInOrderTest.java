package id.ac.ui.cs.advprog.backendbuysell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;
public class ListingInOrderTest {
    ListingInOrder listingInOrder;
    Errors validationResult;
    @BeforeEach
    public void setUp(){
        Listing listing = new Listing("Jeans Pria", "abc", 20, 55_000L);
        this.listingInOrder = new ListingInOrder(listing, 2);
        this.listingInOrder.setOrderId(1L);
    }

    @Test
    public void whenHasNullListing_thenInvalid(){
        listingInOrder.setListing(null);
        validationResult = listingInOrder.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    public void whenHasNullQuantity_thenInvalid(){
        listingInOrder.setQuantity(null);
        validationResult = listingInOrder.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    public void whenHasNegativeQuantity_thenInvalid(){
        listingInOrder.setQuantity(-1);
        validationResult = listingInOrder.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    public void whenHasMinimumNonNegativeQuantity_thenValid(){
        listingInOrder.setQuantity(0);
        validationResult = listingInOrder.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    public void whenHasMaximumNonNegativeQuantity_thenValid(){
        listingInOrder.setQuantity(Integer.MAX_VALUE);
        validationResult = listingInOrder.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    public void whenCalculateSubTotalPrice_setSubTotalPrice(){
        Long price = 60_000L;
        Integer quantity = 3;
        listingInOrder.getListing().setPrice(price);
        listingInOrder.setQuantity(quantity);
        listingInOrder.calculateSubTotalPrice();

        assertEquals(price * quantity, listingInOrder.getSubTotalPrice());
    }

    @Test
    public void whenCalculateSubTotalPriceAndOverflow_throwArithmeticException(){
        Long price = Long.MAX_VALUE;
        Integer quantity = 3;
        listingInOrder.getListing().setPrice(price);
        listingInOrder.setQuantity(quantity);

        assertThrows(ArithmeticException.class, () -> listingInOrder.calculateSubTotalPrice());
    }

    @Test
    public void whenCalculateSubTotalPriceWithNullListing_throwError(){
        listingInOrder.setListing(null);
        assertThrows(Error.class, () -> listingInOrder.calculateSubTotalPrice());
    }

    @Test
    public void whenCalculateSubTotalPriceWithNullPrice_throwError(){
        listingInOrder.getListing().setPrice(null);
        assertThrows(Error.class, () -> listingInOrder.calculateSubTotalPrice());
    }

    @Test
    public void whenCalculateSubTotalPriceWithNullQuantity_throwError(){
        listingInOrder.setQuantity(null);
        assertThrows(Error.class, () -> listingInOrder.calculateSubTotalPrice());
    }

    @Test
    public void whenCallConstructor_thenSetSubtotalPrice(){
        Listing listing = this.listingInOrder.getListing();
        Long price = listing.getPrice();
        Integer quantity = 3;

        ListingInOrder listingInOrder1 = new ListingInOrder(listing, quantity);

        Long expectedSubTotalPrice = price * quantity;
        assertEquals(expectedSubTotalPrice, listingInOrder1.getSubTotalPrice());
    }

}
