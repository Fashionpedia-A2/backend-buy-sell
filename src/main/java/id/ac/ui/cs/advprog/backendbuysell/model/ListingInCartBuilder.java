package id.ac.ui.cs.advprog.backendbuysell.model;

import lombok.Setter;


public class ListingInCartBuilder {
    @Setter
    ListingInCart listingInCart;

    public ListingInCartBuilder(){
        listingInCart = new ListingInCart();
    }
    public ListingInCartBuilder setQuantity(int quantity){
        listingInCart.setQuantity(quantity);
        return this;
    }

    public ListingInCartBuilder setListing(Listing listing){
        listingInCart.setListing(listing);
        return this;
    }

    public ListingInCartBuilder setCart(Cart cart){
        listingInCart.setCart(cart);
        return this;
    }

    public ListingInCart build(){
        return listingInCart;
    }
}
