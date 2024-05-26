package id.ac.ui.cs.advprog.backendbuysell.model;

import id.ac.ui.cs.advprog.backendbuysell.builder.ListingInCartBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListingInCartBuilderTest {
    @Test
    public void testSetQuantity() {
        ListingInCartBuilder builder = new ListingInCartBuilder();
        int quantity = 2;

        builder.setQuantity(quantity);

        assertEquals(quantity, builder.build().getQuantity());
    }

    @Test
    public void testSetListing() {
        ListingInCartBuilder builder = new ListingInCartBuilder();
        Listing listing = new Listing();

        builder.setListing(listing);

        assertEquals(listing, builder.build().getListing());
    }

    @Test
    public void testSetCart() {
        ListingInCartBuilder builder = new ListingInCartBuilder();
        Cart cart = new Cart();

        builder.setCart(cart);

        assertEquals(cart, builder.build().getCart());
    }
}
