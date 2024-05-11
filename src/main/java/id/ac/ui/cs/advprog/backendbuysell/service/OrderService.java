package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    OrderListResponseDTO getAll(OrderListRequestDTO requestDTO);
    Order getById(Long id);
    OrderListRequestDTO getAllSellerOrders(String sellerId, OrderListRequestDTO requestDTO);
    OrderListRequestDTO getAllBuyerOrders(String buyerId, OrderListRequestDTO requestDTO);
    Order updateOrderStatus(Long id, String status, String sellerId);
}
