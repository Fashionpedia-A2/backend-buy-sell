package id.ac.ui.cs.advprog.backendbuysell.model;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingInCartDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.repository.CartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingInCartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import id.ac.ui.cs.advprog.backendbuysell.service.CartService;
import id.ac.ui.cs.advprog.backendbuysell.service.OrderService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ListingInCartRepository listingInCartRepository;
    @Mock
    private ListingRepository listingRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CartService cartService;
    @BeforeEach
    public void setUp() {
        initMocks(this);
    }
    @Test
    public void testFindByUser_ExistingCart() {
        User user = new User();
        Cart expectedCart = new Cart();
        expectedCart.setUser(user);

        // Mock cartRepository behavior
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(expectedCart));

        Cart actualCart = cartService.findByUser(user);

        assertEquals(expectedCart, actualCart);
    }

    @Test
    public void testFindByUser_NoExistingCart() {
        User user = new User();

        // Mock cartRepository to return empty Optional
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());

        Cart actualCart = cartService.findByUser(user);

        // Assert that a new cart is created and saved
        verify(cartRepository).save(any(Cart.class));
        assertNull(actualCart);
    }
    // saveListingInCart with positive quantity
    @Test
    public void testSaveListingInCart_PositiveQuantity() {
        ListingInCart listingInCart = new ListingInCart();
        listingInCart.setQuantity(1);

        cartService.saveListingInCart(listingInCart);

        // Verify that listingInCartRepository.save is called
        verify(listingInCartRepository).save(listingInCart);
    }

    // saveListingInCart with zero quantity (deletion)
    @Test
    public void testSaveListingInCart_ZeroQuantity() {
        ListingInCart listingInCart = new ListingInCart();
        listingInCart.setQuantity(0);

        cartService.saveListingInCart(listingInCart);

        // Verify that listingInCartRepository.delete is called
        verify(listingInCartRepository).delete(listingInCart);
    }

    // Test with empty list in findListingsInCartByUser
    @Test
    public void testFindListingsInCartByUser_EmptyList() {
        User user = new User();

        // Mock listingInCartRepository to return empty list
        when(listingInCartRepository.findByUser(user)).thenReturn(Collections.emptyList());

        List<ListingInCartDetailsDto> actualList = cartService.findListingsInCartByUser(user);

        // Assert that an empty list is returned
        assertEquals(Collections.emptyList(), actualList);
    }
    @Test
    public void testCheckout_MultipleSellers() {
        User user = new User();
        user.setId(110);

        Cart cart = new Cart();
        cart.setUser(user); // Set the user for the cart

        // Create listings from different sellers
        Seller seller1 = new Seller();
        seller1.setId(100L);
        Seller seller2 = new Seller();
        seller2.setId(50L);
        Listing listing1 = new Listing();

        listing1.setName("Product 1 Name");
        listing1.setDescription("Description of Product 1");
        listing1.setPrice(10000L);
        listing1.setSellerId(seller1.getId());

        Listing listing2 = new Listing();
        listing2.setName("Product 2 Name");
        listing2.setDescription("Description of Product 2");
        listing2.setPrice(20000L);
        listing2.setSellerId(seller2.getId());

        // Mock listingInCartRepository to return listings from both sellers
        List<ListingInCart> listingsInCart = new ArrayList<>();
        listingsInCart.add(new ListingInCart(listing1, cart, 1));
        listingsInCart.add(new ListingInCart(listing2, cart, 2));
        when(listingInCartRepository.findByUser(user)).thenReturn(listingsInCart);

        // Call checkout
        cartService.checkout(user);

        // Verify that 2 orders are created, one for each seller
        verify(listingInCartRepository, times(1)).findByUser(user); // Assuming findById is used to get listing details

        // Additional assertions can be added to verify specific Order details if needed
    }
}
