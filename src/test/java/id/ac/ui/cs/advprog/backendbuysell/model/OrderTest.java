package id.ac.ui.cs.advprog.backendbuysell.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    Order order;
    Errors validationResult;

    @BeforeEach
    void setUp() {
        Listing listing1 = Listing.builder().name("Celana Denim").sellerId(1L).stock(10).price(100_000L).build();
        Listing listing2 = Listing.builder().name("Kaos").sellerId(1L).stock(10).price(35_000L).build();
        Listing listing3 = Listing.builder().name("Kerudung").sellerId(1L).stock(40).price(155_000L).build();


        ListingInOrder listingInOrder1 = new ListingInOrder(listing1, 1);
        ListingInOrder listingInOrder2 = new ListingInOrder(listing2, 2);
        ListingInOrder listingInOrder3 = new ListingInOrder(listing3, 3);

        List<ListingInOrder> listingInOrders = new ArrayList<>();
        listingInOrders.add(listingInOrder1);
        listingInOrders.add(listingInOrder2);
        listingInOrders.add(listingInOrder3);

        order = new Order();
        order.setListingInOrders(listingInOrders);
        order.setBuyerId(99L);
        order.setSellerId(listing1.getSellerId());
    }

    @Test
    void whenSetStatusWithInvalidStatus_thenHasErrorInValidation() {
        order.setStatus(null);
        validationResult = order.validate();
        assertTrue(validationResult.hasErrors());
        assertEquals(1, validationResult.getErrorCount());
    }

    @Test
    void whenSetStatusWithValidStatusAllCapital_thenNoErrorInValidation() {
        order.setStatus("MENUNGGU_PEMBAYARAN");
        validationResult = order.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void whenSetStatusWithValidStatusSomeLowerCase_thenNoErrorInValidation() {
        order.setStatus("dIkiRIm");
        validationResult = order.validate();
        assertFalse(validationResult.hasErrors());
    }

    @Test
    void whenCalculateTotalPrice_thenSetTotalPrice() {
        List<ListingInOrder> listingInOrders = order.getListingInOrders();
        Long expectedTotalPrice = 0L;
        for (ListingInOrder listingInOrder : listingInOrders) {
            expectedTotalPrice += listingInOrder.getSubTotalPrice();
        }
        order.calculateTotalPrice();

        assertEquals(expectedTotalPrice, order.getTotalPrice());
    }

    @Test
    void whenCalculateTotalPriceAndOverflow_thenThrowArithmeticException() {
        order.getListingInOrders().forEach(listingInOrder -> listingInOrder.setSubTotalPrice(Long.MAX_VALUE));
        assertThrows(ArithmeticException.class, () -> order.calculateTotalPrice());
    }

    @Test
    void whenSetEmptyListingInOrders_thenSetTotalPriceToZero() {
        order.setListingInOrders(new ArrayList<>());
        assertEquals(0L, order.getTotalPrice());
    }

    @Test
    void whenAddListingInOrder_thenUpdateTotalPrice(){
        ListingInOrder listingInOrder = order.getListingInOrders().getFirst();
        Long oldTotalPrice = order.getTotalPrice();
        Long expectedTotalPrice = oldTotalPrice + listingInOrder.getSubTotalPrice();

        order.addListingInOrder(listingInOrder);
        assertEquals(expectedTotalPrice, order.getTotalPrice());
    }

    @Test
    void whenRemoveListingInOrder_thenUpdateTotalPrice(){
        ListingInOrder listingInOrder = order.getListingInOrders().getFirst();
        Long oldTotalPrice = order.getTotalPrice();
        Long expectedTotalPrice = oldTotalPrice - listingInOrder.getSubTotalPrice();

        order.removeListingInOrder(listingInOrder);
        assertEquals(expectedTotalPrice, order.getTotalPrice());
    }

    @Test
    void whenSellerIsNull_thenHasErrorInValidation() {
        order.setSellerId(null);
        validationResult = order.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void whenBuyerIsNull_thenHasErrorInValidation() {
        order.setBuyerId(null);
        validationResult = order.validate();
        assertTrue(validationResult.hasErrors());
    }
}
