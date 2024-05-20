package id.ac.ui.cs.advprog.backendbuysell.dto;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.ListingInOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingInOrderDTO {
    private Long id;
    private Listing listing;
    private Integer quantity;
    private Long subTotalPrice;


    public static ListingInOrderDTO fromListingInOrder(ListingInOrder listingInOrder) {
        ListingInOrderDTO listingInOrderDTO = new ListingInOrderDTO();
        listingInOrderDTO.setId(listingInOrder.getId());
        listingInOrderDTO.setListing(listingInOrder.getListing());
        listingInOrderDTO.setQuantity(listingInOrder.getQuantity());
        listingInOrderDTO.setSubTotalPrice(listingInOrder.getSubTotalPrice());
        return listingInOrderDTO;
    }
}
