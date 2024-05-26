package id.ac.ui.cs.advprog.backendbuysell.dto;

import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import lombok.Getter;

@Getter
public class SellerDetailsDto {

    private Long id;
    private String name;
    public SellerDetailsDto(Seller seller){
        id = seller.getId();
        name = seller.getName();
    }
}