package id.ac.ui.cs.advprog.backendbuysell.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class OrderStatusTest {
    @Test
    void whenCallFromStringWithValidStatusAllUpperCase_returnOrderStatus(){
        OrderStatus status = OrderStatus.fromString("DIKIRIM");
        assertEquals(OrderStatus.DIKIRIM, status);
    }

    @Test
    void whenCallFromStringWithValidStatusSomeLowerCase_returnOrderStatus(){
        OrderStatus status = OrderStatus.fromString("DikIRim");
        assertEquals(OrderStatus.DIKIRIM, status);
    }

    @Test
    void whenCallFromStringWithInvalidStatus_throwIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> OrderStatus.fromString("mencintai"));
    }
}
