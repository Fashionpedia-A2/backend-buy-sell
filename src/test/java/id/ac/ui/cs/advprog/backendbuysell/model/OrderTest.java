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
        Listing listing1 = new Listing("Denim", "tukang_baju", 10, 100_000L);
        Listing listing2 = new Listing("Kaos", "tukang_baju", 10, 35_000L);
        Listing listing3 = new Listing("Kerudung", "tukang_baju", 10, 20_000L);

        ListingInOrder listingInOrder1 = new ListingInOrder(listing1, 1);
        ListingInOrder listingInOrder2 = new ListingInOrder(listing2, 2);
        ListingInOrder listingInOrder3 = new ListingInOrder(listing3, 3);

        List<ListingInOrder> listingInOrders = new ArrayList<>();
        listingInOrders.add(listingInOrder1);
        listingInOrders.add(listingInOrder2);
        listingInOrders.add(listingInOrder3);

        order = new Order();
        order.setListingInOrders(listingInOrders);
        order.setBuyerId("pembeli");
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
    void whenSellerIdIsNull_thenHasErrorInValidation() {
        order.setSellerId(null);
        validationResult = order.validate();
        assertTrue(validationResult.hasErrors());
    }

    @Test
    void whenBuyerIdIsNull_thenHasErrorInValidation() {
        order.setBuyerId(null);
        validationResult = order.validate();
        assertTrue(validationResult.hasErrors());
    }
}
