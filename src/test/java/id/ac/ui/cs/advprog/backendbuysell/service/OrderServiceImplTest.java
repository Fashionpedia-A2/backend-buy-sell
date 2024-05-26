package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.OrderDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.enums.OrderStatus;
import id.ac.ui.cs.advprog.backendbuysell.exception.FieldValidationException;
import id.ac.ui.cs.advprog.backendbuysell.exception.ForbiddenException;
import id.ac.ui.cs.advprog.backendbuysell.model.*;
import id.ac.ui.cs.advprog.backendbuysell.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    OrderRepository orderRepository;

    @Mock
    BuyerService buyerService;

    @InjectMocks
    OrderServiceImpl orderService;


    List<Order> orders;
    List<OrderDTO> orderDTOS;
    OrderListRequestDTO orderListRequestDTO;
    SimpleDateFormat dateFormatter;

    @BeforeEach
    void setUp() throws Exception {
        Listing listing1 = Listing.builder().name("Celana Denim").sellerId(1L).stock(10).price(100_000L).build();
        Listing listing2 = Listing.builder().name("Kaos").sellerId(1L).stock(10).price(35_000L).build();
        Listing listing3 = Listing.builder().name("Kerudung").sellerId(2L).stock(40).price(155_000L).build();
        Listing listing4 = Listing.builder().name("Gamis Shimmer").sellerId(2L).stock(33).price(200_000L).build();


        ListingInOrder listingInOrder1 = new ListingInOrder(listing1, 1);
        ListingInOrder listingInOrder2 = new ListingInOrder(listing2, 2);
        ListingInOrder listingInOrder3 = new ListingInOrder(listing3, 3);
        ListingInOrder listingInOrder4 = new ListingInOrder(listing4, 1);

        List<ListingInOrder> listingInOrders1 = new ArrayList<>();
        listingInOrders1.add(listingInOrder1);
        List<ListingInOrder> listingInOrders2 = new ArrayList<>();
        listingInOrders2.add(listingInOrder2);
        List<ListingInOrder> listingInOrders3 = new ArrayList<>();
        listingInOrders3.add(listingInOrder3);
        List<ListingInOrder> listingInOrders4 = new ArrayList<>();
        listingInOrders4.add(listingInOrder4);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        Order order1 = new Order();
        order1.setId(1L);
        order1.setListingInOrders(listingInOrders1);
        order1.setBuyerId(1L);
        order1.setSellerId(listing1.getSellerId());
        order1.setStatus(OrderStatus.MENUNGGU_PEMBAYARAN.name());
        order1.setCreatedAt(dateFormatter.parse("01/01/2024"));

        Order order2 = new Order();
        order2.setId(2L);
        order2.setListingInOrders(listingInOrders2);
        order2.setBuyerId(2L);
        order2.setSellerId(listing3.getSellerId());
        order2.setStatus(OrderStatus.MENUNGGU_PROSES.name());
        order2.setCreatedAt(dateFormatter.parse("02/01/2024"));

        Order order3 = new Order();
        order3.setId(3L);
        order3.setListingInOrders(listingInOrders3);
        order3.setBuyerId(1L);
        order3.setSellerId(listing1.getSellerId());
        order3.setStatus(OrderStatus.DIKIRIM.name());
        order3.setCreatedAt(dateFormatter.parse("03/01/2024"));

        Order order4 = new Order();
        order4.setId(4L);
        order4.setListingInOrders(listingInOrders4);
        order4.setBuyerId(2L);
        order4.setSellerId(listing3.getSellerId());
        order4.setStatus(OrderStatus.DIBATALKAN.name());
        order4.setCreatedAt(dateFormatter.parse("04/01/2024"));

        orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);

        orderDTOS = new ArrayList<>();
        for(Order order: orders){
            Buyer buyer = Buyer.builder().id(order.getBuyerId()).build();
            orderDTOS.add(OrderDTO.fromOrder(order, buyer));
        }

        orderListRequestDTO = OrderListRequestDTO.builder().pageable(PageRequest.of(0, 20)).build();
    }

    @Test
    void whenCreateOrder_thenSaveAndReturnOrder() {
        Order order = orders.getFirst();
        doReturn(order).when(orderRepository).save(any(Order.class));
        Buyer buyer = Buyer.builder().id(order.getId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        OrderDTO result = orderService.create(order);

        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(order.getSellerId(), result.getSellerId());
    }

    @Test
    void whenCreateOrderWithInvalidField_thenThrowFieldValidationException() {
        Order order = orders.getFirst();
        order.setStatus("TERBANNG");
        assertThrows(FieldValidationException.class, () -> orderService.create(order));
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void whenGetAllOrder_thenReturnOrderListDTO() {
        Page<Order> page = new PageImpl<>(this.orders);
        doReturn(page).when(orderRepository).findAll(ArgumentMatchers.<Specification<Order>>any(), any(Pageable.class));
        Buyer buyer = Buyer.builder().id(this.orders.getFirst().getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        List<OrderDTO> result = orderService.getAll(orderListRequestDTO).getOrders();
        assertEquals(this.orders.size(), result.size());
    }

    @Test
    void whenGetAllOrderFilteredByStatus_thenReturnOrderListDTO() {
        List<Order> cancelledOrders = new ArrayList<>();
        cancelledOrders.add(this.orders.getLast());
        Page<Order> page = new PageImpl<>(cancelledOrders);
        doReturn(page).when(orderRepository).findAll(ArgumentMatchers.<Specification<Order>>any(), any(Pageable.class));
        Buyer buyer = Buyer.builder().id(cancelledOrders.getFirst().getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        orderListRequestDTO.setStatus(OrderStatus.DIBATALKAN.name());
        List<OrderDTO> result = orderService.getAll(orderListRequestDTO).getOrders();

        assertEquals(cancelledOrders.size(), result.size());
    }

    @Test
    void whenGetAllOrderFilteredByCreatedAtStart_thenReturnOrderListDTO() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(this.orders.get(2));
        orders.add(this.orders.get(3));
        Page<Order> page = new PageImpl<>(orders);
        doReturn(page).when(orderRepository).findAll(ArgumentMatchers.<Specification<Order>>any(), any(Pageable.class));
        Buyer buyer = Buyer.builder().id(this.orders.getFirst().getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        Date startDate = dateFormatter.parse("01/03/2024");
        orderListRequestDTO.setCreatedAtStart(startDate);
        List<OrderDTO> result = orderService.getAll(orderListRequestDTO).getOrders();

        assertEquals(orders.size(), result.size());
    }

    @Test
    void whenGetAllOrderFilteredByCreatedAtEnd_thenReturnOrderListDTO() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(this.orders.get(0));
        orders.add(this.orders.get(1));
        Page<Order> page = new PageImpl<>(orders);
        doReturn(page).when(orderRepository).findAll(ArgumentMatchers.<Specification<Order>>any(), any(Pageable.class));
        Buyer buyer = Buyer.builder().id(this.orders.getFirst().getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        Date endDate = dateFormatter.parse("01/02/2024");
        orderListRequestDTO.setCreatedAtEnd(endDate);
        List<OrderDTO> result = orderService.getAll(orderListRequestDTO).getOrders();

        assertEquals(orders.size(), result.size());
    }

    @Test
    void whenGetAllOrderFilteredByCreatedAtRange_thenReturnOrderListDTO() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(this.orders.get(1));
        orders.add(this.orders.get(2));
        Page<Order> page = new PageImpl<>(orders);
        doReturn(page).when(orderRepository).findAll(ArgumentMatchers.<Specification<Order>>any(), any(Pageable.class));
        Buyer buyer = Buyer.builder().id(this.orders.get(1).getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        Date startDate = dateFormatter.parse("01/02/2024");
        Date endDate = dateFormatter.parse("01/03/2024");
        orderListRequestDTO.setCreatedAtStart(startDate);
        orderListRequestDTO.setCreatedAtEnd(endDate);
        List<OrderDTO> result = orderService.getAll(orderListRequestDTO).getOrders();

        assertEquals(orders.size(), result.size());
    }

    @Test
    void whenGetAllSellerOrders_thenReturnOrderListDTO() {
        List<Order> orders = new ArrayList<>();
        orders.add(this.orders.get(0));
        orders.add(this.orders.get(1));
        Page<Order> page = new PageImpl<>(orders);
        doReturn(page).when(orderRepository).findAll(ArgumentMatchers.<Specification<Order>>any(), any(Pageable.class));
        Buyer buyer = Buyer.builder().id(this.orders.getFirst().getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        orderListRequestDTO.setSellerId(orders.getFirst().getSellerId());
        List<OrderDTO> result = orderService.getAll(orderListRequestDTO).getOrders();

        assertEquals(orders.size(), result.size());
    }

    @Test
    void whenGetAllBuyerOrders_thenReturnOrderListDTO() {
        List<Order> orders = new ArrayList<>();
        orders.add(this.orders.get(0));
        orders.add(this.orders.get(2));
        Page<Order> page = new PageImpl<>(orders);
        doReturn(page).when(orderRepository).findAll(ArgumentMatchers.<Specification<Order>>any(), any(Pageable.class));
        Buyer buyer = Buyer.builder().id(this.orders.getFirst().getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        orderListRequestDTO.setBuyerId(orders.getFirst().getBuyerId());
        List<OrderDTO> result = orderService.getAll(orderListRequestDTO).getOrders();

        assertEquals(orders.size(), result.size());
    }

    @Test
    void whenGetById_thenReturnPresentOptionalOrder() {
        Order order = orders.getFirst();
        doReturn(Optional.of(order)).when(orderRepository).findById(any(Long.class));
        Buyer buyer = Buyer.builder().id(order.getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        Optional<OrderDTO> result = orderService.getById(order.getId());
        assertTrue(result.isPresent());
        assertEquals(order.getId(), result.get().getId());
    }

    @Test
    void whenGetByIdWithNonExistingId_thenReturnEmptyOptionalOrder() {
        doReturn(Optional.empty()).when(orderRepository).findById(any(Long.class));

        Optional<OrderDTO> result = orderService.getById(1000L);
        assertTrue(result.isEmpty());
    }

    @Test
    void whenSetStatusOrder_thenSaveAndReturnOrder() {
        Order order = orders.getFirst();
        order.setStatus(OrderStatus.DIPROSES.name());
        doReturn(Optional.of(order)).when(orderRepository).findById(any(Long.class));
        doReturn(order).when(orderRepository).save(any(Order.class));
        Buyer buyer = Buyer.builder().id(order.getBuyerId()).build();
        doReturn(buyer).when(buyerService).findById(any(Long.class));

        OrderDTO result = orderService.updateOrderStatus(order.getId(), OrderStatus.DIPROSES.name(), order.getSellerId());
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(order.getStatus(), result.getStatus());
    }

    @Test
    void whenSetStatusOrderWithNonExistingId_thenThrowNoSuchElementException() {
        Order order = orders.getFirst();
        doReturn(Optional.empty()).when(orderRepository).findById(any(Long.class));
        assertThrows(NoSuchElementException.class,
                     () -> orderService.updateOrderStatus(111L, OrderStatus.DIPROSES.name(), order.getSellerId()));
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void whenSetStatusOrderWithInvalidStatus_thenThrowNoFieldValidationException() {
        Order order = orders.getFirst();
        doReturn(Optional.of(order)).when(orderRepository).findById(any(Long.class));
        assertThrows(FieldValidationException.class,
                     () -> orderService.updateOrderStatus(order.getId(), "TERBAANG", order.getSellerId()));
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void whenSetStatusOrderByUnauthorizedSeller_thenThrowForbiddenException() {
        Order order = orders.getFirst();
        doReturn(Optional.of(order)).when(orderRepository).findById(any(Long.class));
        assertThrows(ForbiddenException.class,
                     () -> orderService.updateOrderStatus(order.getId(), OrderStatus.DIPROSES.name(), -1L));
        verify(orderRepository, times(0)).save(any(Order.class));
    }
}
