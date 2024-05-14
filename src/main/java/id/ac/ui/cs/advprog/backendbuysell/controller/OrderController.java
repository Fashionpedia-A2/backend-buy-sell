package id.ac.ui.cs.advprog.backendbuysell.controller;

import id.ac.ui.cs.advprog.backendbuysell.dto.ApiResponse;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.OrderListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.exception.FieldValidationException;
import id.ac.ui.cs.advprog.backendbuysell.exception.ForbiddenException;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;
import id.ac.ui.cs.advprog.backendbuysell.service.OrderService;
import id.ac.ui.cs.advprog.backendbuysell.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody Order order) {
        ApiResponse<Order> response;
        try {
            Order createdOrder = orderService.create(order);
            response = ApiResponse.success(createdOrder, "Order created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (FieldValidationException e) {
            List<String> errors = e.getErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage).toList();
            response = ApiResponse.failed(errors, "Validation failed");
            return ResponseEntity.badRequest().body(response);
        } catch (ArithmeticException e){
            response = ApiResponse.failed(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        ApiResponse<Order> response;
        Optional<Order> result = orderService.getById(id);
        if (result.isPresent()) {
            response = ApiResponse.success(result.get());
            return ResponseEntity.ok(response);
        } else {
            response = ApiResponse.failed("Order with id " + id + " does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/order")
    public ResponseEntity<ApiResponse<OrderListResponseDTO>> getAllOrders(
            OrderListRequestDTO request,
            @PageableDefault(page = 0, size = 40) @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        request.setPageable(pageable);
        OrderListResponseDTO result = orderService.getAll(request);
        ApiResponse<OrderListResponseDTO> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buyer/order")
    public ResponseEntity<ApiResponse<OrderListResponseDTO>> getAllBuyerOrders(
            OrderListRequestDTO request,
            @PageableDefault(page = 0, size = 40) @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestHeader("Authorization") String token) {
        String buyerId = JwtHelper.getUserIdFromToken(token);
        request.setPageable(pageable);
        OrderListResponseDTO result = orderService.getAllBuyerOrders(buyerId, request);
        ApiResponse<OrderListResponseDTO> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/seller/status-order/{id}")
    public ResponseEntity<ApiResponse<Order>> getAllSellerOrders(
            @PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody String status) {
        ApiResponse<Order> response;
        try {
            String sellerId = JwtHelper.getUserIdFromToken(token);
            Order result = orderService.updateOrderStatus(id, status, sellerId);
            response = ApiResponse.success(result, "Order status successfully created.");
            return ResponseEntity.ok(response);
        } catch (FieldValidationException e) {
            List<String> errors = e.getErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage).toList();
            response = ApiResponse.failed(errors, "Validation failed");
            return ResponseEntity.badRequest().body(response);
        } catch (ForbiddenException e) {
            response = ApiResponse.failed(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}
