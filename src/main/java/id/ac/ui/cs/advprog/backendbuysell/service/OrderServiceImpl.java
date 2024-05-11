package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;

public class OrderServiceImpl implements OrderService {
    public Order create(Order order) {
        return null;
    }

    public OrderListResponseDTO getAll(OrderListRequestDTO requestDTO) {
        return null;
    }

    public Order getById(Long id) {
        return null;
    }

    public OrderListRequestDTO getAllSellerOrders(String sellerId, OrderListRequestDTO requestDTO) {
        return null;
    }

    public OrderListRequestDTO getAllBuyerOrders(String buyerId, OrderListRequestDTO requestDTO) {
        return null;
    }

    public Order updateOrderStatus(Long id, String status, String sellerId) {
        return null;
    }
}
