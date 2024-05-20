package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingInCartDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.model.*;
import id.ac.ui.cs.advprog.backendbuysell.repository.CartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingInCartRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.ListingRepository;
import id.ac.ui.cs.advprog.backendbuysell.repository.SellerRepository;
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

    @Autowired
    private SellerService sellerService;


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
            Seller seller = sellerService.findById(lic.getListing().getSellerId());
            dto.setListing(new ListingDetailsDto(lic.getListing(), seller));
            dto.setQuantity(lic.getQuantity());
            output.add(dto);
        }
        return output;
    }

    public void checkout(User user){
        List<ListingInCart> list = listingInCartRepository.findByUser(user);
        Map<Long, Order> sellerToOrderMap = new HashMap<>();

        for (ListingInCart lic: list){
            //listingInCartRepository.delete(lic);
            Listing listing = lic.getListing();
            Long sellerId = lic.getListing().getSellerId();
            int quantity = lic.getQuantity();

            Order order = sellerToOrderMap.get(sellerId);
            if (order == null){
                order = new Order();
                order.setSellerId(sellerId);
                order.setBuyerId(Long.valueOf(user.getId()));
                sellerToOrderMap.put(sellerId, order);
            }
            order = sellerToOrderMap.get(sellerId);
            ListingInOrder listingInOrder = new ListingInOrder(listing, quantity);
            order.addListingInOrder(listingInOrder);
        }
    }
}
