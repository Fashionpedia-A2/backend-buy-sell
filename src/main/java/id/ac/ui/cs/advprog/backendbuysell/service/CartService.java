package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingInCartDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.model.*;
import id.ac.ui.cs.advprog.backendbuysell.repository.CartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingInCartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public void saveListingInCart(ListingInCart listingInCart){
        if (listingInCart.getQuantity() <= 0){
            listingInCartRepository.delete(listingInCart);
        } else{
            listingInCartRepository.save(listingInCart);
        }

    }

    public List<ListingInCartDetailsDto> findListingsInCartByUser(User user) {
        List<ListingInCart> list = listingInCartRepository.findByUser(user);
        List<ListingInCartDetailsDto> output = new ArrayList<ListingInCartDetailsDto>();
        for (ListingInCart lic : list){
            ListingInCartDetailsDto dto = new ListingInCartDetailsDto();
            dto.setListing(new ListingDetailsDto(lic.getListing()));
            dto.setQuantity(lic.getQuantity());
            output.add(dto);
        }
        return output;
    }

    public void checkout(User user){
        List<ListingInCart> list = listingInCartRepository.findByUser(user);
        Map<Long, Order> peta = new HashMap<>();

        for (ListingInCart lic: list){
            //listingInCartRepository.delete(lic);
            Listing listing = lic.getListing();
            Seller seller = lic.getListing().getSeller();
            int quantity = lic.getQuantity();
            long sellerId = lic.getListing().getSeller().getId();

            Order order = peta.get(sellerId);
            if (order == null){
                order = new Order();
                order.setSeller(seller);
                order.setBuyerId(Long.valueOf(user.getId()));
                peta.put(sellerId, order);

            }
            ListingInOrder listingInOrder = new ListingInOrder(listing, quantity);
        }
        Collection<Order> orders = peta.values();
        for (Order order:  orders){
            // TODO: Hamdi, tolong set atribut yg perlu (createdAt, updatedAt, totalPrice, dll) ~fred
            //order.setCreatedAt(today);
            //order.setUpdatedAt(today);
            //order.setPaymentId(today);
        }

    }
}
