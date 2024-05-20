package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order create(Order order);
    OrderListResponseDTO getAll(OrderListRequestDTO requestDTO);
    Optional<Order> getById(Long id);
    OrderListResponseDTO getAllSellerOrders(Long sellerId, OrderListRequestDTO requestDTO);
    OrderListResponseDTO getAllBuyerOrders(Long buyerId, OrderListRequestDTO requestDTO);
    Order updateOrderStatus(Long id, String status, Long sellerId);
}
