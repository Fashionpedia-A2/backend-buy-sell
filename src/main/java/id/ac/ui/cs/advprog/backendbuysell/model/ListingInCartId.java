package id.ac.ui.cs.advprog.backendbuysell.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ListingInCartId implements Serializable {

    private Listing listing;
    private Cart cart;

    // standard getters and setters
}