package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellerTest {
    @Test
    public void testGettersAndSetters() {
        Seller seller = new Seller();

        seller.setName("John Doe");
        assertEquals("John Doe", seller.getName());

        seller.setId(1L);
        assertEquals(1L, seller.getId().longValue()); // Use longValue() for Long comparison
    }
    @Test
    public void testToString() throws JsonProcessingException {
        Seller seller = new Seller();
        seller.setName("John Doe");
        seller.setId(1L);

        // Mock ObjectMapper to avoid actual JSON serialization
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        // Define expected JSON ignoring the "listing" field (as it's a one-to-many relationship)
        Mockito.when(mapper.writeValueAsString(seller)).thenReturn("{\"id\":1,\"name\":\"John Doe\",\"listing\":null}");

        assertEquals("{\"id\":1,\"name\":\"John Doe\",\"listing\":null}", seller.toString());
    }

}
