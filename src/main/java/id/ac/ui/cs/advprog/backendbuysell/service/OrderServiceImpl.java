package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.dto.OrderDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.exception.FieldValidationException;
import id.ac.ui.cs.advprog.backendbuysell.exception.ForbiddenException;
import id.ac.ui.cs.advprog.backendbuysell.model.Buyer;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;
import id.ac.ui.cs.advprog.backendbuysell.repository.OrderRepository;
import id.ac.ui.cs.advprog.backendbuysell.utils.ListingSearchQueryBuilder;
import id.ac.ui.cs.advprog.backendbuysell.utils.OrderSearchQueryBuilder;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BuyerService buyerService;

    public OrderDTO create(Order order) {
        Errors validationResult = order.validate();
        if (validationResult.hasErrors()) {
            throw new FieldValidationException(validationResult.getAllErrors(), "Validation Error");
        }
        Order savedOrder = orderRepository.save(order);
        Buyer buyer = buyerService.findById(order.getBuyerId());
        return OrderDTO.fromOrder(savedOrder, buyer);
    }

    public OrderListResponseDTO getAll(OrderListRequestDTO requestDTO) {
        Specification<Order> specification = OrderSearchQueryBuilder.buildSpecification(requestDTO);
        Page<Order> page = orderRepository.findAll(specification, requestDTO.getPageable());
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : page.getContent()) {
            Buyer buyer = buyerService.findById(order.getBuyerId());
            orderDTOs.add(OrderDTO.fromOrder(order, buyer));
        }
        return OrderListResponseDTO
                .builder()
                .orders(orderDTOs)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .build();
    }

    public Optional<OrderDTO> getById(Long id) {
        Optional<Order> result = orderRepository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        Order order = result.get();;
        Buyer buyer = buyerService.findById(order.getBuyerId());
        return Optional.of(OrderDTO.fromOrder(order, buyer));
    }

    public OrderListResponseDTO getAllSellerOrders(Long sellerId, OrderListRequestDTO requestDTO) {
        requestDTO.setSellerId(sellerId);
        return this.getAll(requestDTO);
    }

    public OrderListResponseDTO getAllBuyerOrders(Long buyerId, OrderListRequestDTO requestDTO) {
        requestDTO.setBuyerId(buyerId);
        return this.getAll(requestDTO);
    }

    public OrderDTO updateOrderStatus(Long id, String status, Long sellerId) {
        Order order = orderRepository.findById(id).orElseThrow();
        if (!isAuthenticated(order, sellerId)) {
            throw new ForbiddenException("User is not authorized to perform action on this order");
        }
        order.setStatus(status);
        Errors validationResult = order.validate();
        if (validationResult.hasErrors()) {
            throw new FieldValidationException(validationResult.getAllErrors(), "Validation Error");
        }
        Order savedOrder = orderRepository.save(order);
        Buyer buyer = buyerService.findById(order.getBuyerId());
        return OrderDTO.fromOrder(savedOrder, buyer);
    }

    private boolean isAuthenticated(Order order, Long sellerId) {
        return order.getSellerId().equals(sellerId);
    }

}
