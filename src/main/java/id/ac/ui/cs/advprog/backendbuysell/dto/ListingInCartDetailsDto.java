package id.ac.ui.cs.advprog.backendbuysell.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingInCartDetailsDto {
    private ListingDetailsDto listing;
    private int quantity;
}
