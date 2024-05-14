package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.model.Cart;
import id.ac.ui.cs.advprog.backendbuysell.model.ListingInCart;
import id.ac.ui.cs.advprog.backendbuysell.repository.CartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingInCartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ListingInCartRepository listingInCartRepository;

    @Autowired
    private ListingRepository listingRepository;


    public Cart findByUser(User user) {
        Optional<Cart> cart =  cartRepository.findByUser(user);
        if (cart.isEmpty()){
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        } else{
            return cart.get();
        }
    }

    public ListingInCart saveListingInCart(ListingInCart listingInCart){
        return listingInCartRepository.save(listingInCart);
    }
}
