package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListingInCartTest {
    @Test
    public void testConstructor() {
        Listing listing = new Listing();
        Cart cart = new Cart();
        ListingInCart listingInCart = new ListingInCart();
        listingInCart.setListing(listing);
        listingInCart.setCart(cart);
        listingInCart.setQuantity(2);

        assertEquals(listing, listingInCart.getListing());
        assertEquals(cart, listingInCart.getCart());
        assertEquals(2, listingInCart.getQuantity());
    }

    @Test
    public void testGettersAndSetters() {
        Listing listing = new Listing();
        Cart cart = new Cart();
        ListingInCart listingInCart = new ListingInCart();

        listingInCart.setListing(listing);
        assertEquals(listing, listingInCart.getListing());

        listingInCart.setCart(cart);
        assertEquals(cart, listingInCart.getCart());

        listingInCart.setQuantity(3);
        assertEquals(3, listingInCart.getQuantity());
    }

    @Test
    public void testToString() throws JsonProcessingException {
        Listing listing = new Listing();
        Cart cart = new Cart();
        ListingInCart listingInCart = new ListingInCart();
        listingInCart.setListing(listing);
        listingInCart.setCart(cart);
        listingInCart.setQuantity(3);

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(listingInCart);

        assertEquals(expectedJson, listingInCart.toString());
    }
}
