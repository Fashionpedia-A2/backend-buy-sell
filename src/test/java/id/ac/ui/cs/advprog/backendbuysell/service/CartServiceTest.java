package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Cart;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import id.ac.ui.cs.advprog.backendbuysell.repository.CartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepositoryMock;

    @InjectMocks
    private CartService cartService;

    List<Listing> listings;
    Seller seller;
    ListingListRequestDTO searchCriteriaDTO = ListingListRequestDTO.builder().pageable(PageRequest.of(0, 20)).build();

    @Test
    public void testFindByUser_Success() {
        // Create user and cart objects
        User user = new User();
        Cart expectedCart = new Cart();
        expectedCart.setUser(user);

        // Mock repository behavior
        Optional<Cart> optionalCart = Optional.of(expectedCart);
        when(cartRepositoryMock.findByUser(user)).thenReturn(optionalCart);

        // Call the service method
        Cart actualCart = cartService.findByUser(user);

        // Assert results
        assertNotNull(actualCart);
        assertEquals(expectedCart, actualCart);
    }
}
