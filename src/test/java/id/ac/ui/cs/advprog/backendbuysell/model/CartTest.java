package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
    @Mock
    private User user;

    @Test
    public void testGetterSetter() {
        Cart cart = new Cart();

        cart.setId(1L);
        assertEquals(1L, cart.getId());

        cart.setUser(new User());
        assertNotNull(cart.getUser());
    }

    @Test
    public void testToString_NullUser() throws JsonProcessingException {
        Cart cart = new Cart();
        cart.setId(2L);

        String jsonString = cart.toString();
        // You can assert specific content in the jsonString if needed for your use case
        assertNotNull(jsonString);
    }
}
