package id.ac.ui.cs.advprog.backendbuysell.dto;

import id.ac.ui.cs.advprog.backendbuysell.model.Buyer;
import id.ac.ui.cs.advprog.backendbuysell.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class OrderDTO {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    private Long totalPrice;
    private Long sellerId;
    private Buyer buyer;
    private Long paymentId;
    private List<ListingInOrderDTO> listings;

    public static OrderDTO fromOrder(Order order, Buyer buyer) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setSellerId(order.getSellerId());
        orderDTO.setBuyer(buyer);
        orderDTO.setPaymentId(order.getPaymentId());
        orderDTO.setListings(order.getListingInOrders().stream()
                                     .map(ListingInOrderDTO::fromListingInOrder)
                                     .toList());
        return orderDTO;
    }
}
