package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.OrderDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderDTO create(Order order);
    OrderListResponseDTO getAll(OrderListRequestDTO requestDTO);
    Optional<OrderDTO> getById(Long id);
    OrderListResponseDTO getAllSellerOrders(Long sellerId, OrderListRequestDTO requestDTO);
    OrderListResponseDTO getAllBuyerOrders(Long buyerId, OrderListRequestDTO requestDTO);
    OrderDTO updateOrderStatus(Long id, String status, Long sellerId);
}
