package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


public class ListingInCartBuilder {
    @Setter
    ListingInCart listingInCart;

    public ListingInCartBuilder(){
        listingInCart = new ListingInCart();
    }
    public ListingInCartBuilder setQuantity(int quantity){
        listingInCart.setQuantity(quantity);
    }

    public ListingInCartBuilder setListing(Listing listing){
        listingInCart.setListing(listing);
    }

    public ListingInCartBuilder setCart(Cart cart){
        listingInCart.setCart(cart);
    }

    public ListingInCart build(){
        return listingInCart;
    }
}
