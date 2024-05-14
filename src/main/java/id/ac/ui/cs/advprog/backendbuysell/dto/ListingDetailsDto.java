package id.ac.ui.cs.advprog.backendbuysell.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ListingDetailsDto {
    private long id;
    private String name;
    private String imageUrl;
    private int stock;
    private Long price;
    private String size;
    private String condition;
    private SellerDetailsDto sellerDetailsDto;
    private String status;
    private String category;
    private String description;


    public ListingDetailsDto(Listing listing){
        this.id = id;
        this.name = listing.getName();
        this.imageUrl = listing.getImageUrl();
        this.stock = listing.getStock();
        this.price = listing.getPrice();
        this.size = listing.getSize();
        this.condition = listing.getCondition();
        this.sellerDetailsDto = new SellerDetailsDto(listing.getSeller());
        this.status = listing.getStatus();
        this.category = listing.getCategory();
        this.description = listing.getCategory();
    }
}
