package id.ac.ui.cs.advprog.backendbuysell.controller;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.backendbuysell.auth.service.JwtService;
import id.ac.ui.cs.advprog.backendbuysell.builder.ListingInCartBuilder;
import id.ac.ui.cs.advprog.backendbuysell.dto.CreateListingRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.dto.SellerDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.model.Cart;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.ListingInCart;
import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import id.ac.ui.cs.advprog.backendbuysell.service.CartService;
import id.ac.ui.cs.advprog.backendbuysell.service.ListingServiceBuy;
import id.ac.ui.cs.advprog.backendbuysell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/buyer")
@EnableWebMvc
public class BuyController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ListingServiceBuy listingServiceBuy;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/protected")
    public String protectedEndpoint(HttpServletRequest request) {
        User user = authorizeToken(request);
        return "Yay you have access to protected area";
    }

    private User authorizeToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = header.substring(7);

        User user = jwtService.extractUser(token);
        if (!jwtService.isTokenValid(token, user)) {
            throw new RuntimeException("Invalid token");
        }

        return user;
    }

    @GetMapping("/listing")
    public ResponseEntity<List<ListingDetailsDto>> getAllListing() {
        List<Listing> list = listingServiceBuy.findAll();
        List<ListingDetailsDto> listingDetailsDtoList = new LinkedList<ListingDetailsDto>();
        for(int i = 0; i < list.size(); i++){
            listingDetailsDtoList.add(new ListingDetailsDto(list.get(i)));
        }
        return ResponseEntity.ok(listingDetailsDtoList);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<SellerDetailsDto> getSellerDetails(@PathVariable Long sellerId) {
        try {
            Seller seller = sellerService.findById(sellerId);
            if (seller == null) {
                throw new RuntimeException("Seller with ID " + sellerId + " not found");
            }
            return ResponseEntity.ok(new SellerDetailsDto(seller));
        } catch (Exception e) {
            // Handle other potential exceptions (e.g., database errors)
            return ResponseEntity.internalServerError().build();
        }
    }

    // memasukkan listing ke dalam cart
    @PostMapping("cart/create")
    public ResponseEntity<String> createListingInCart(@RequestBody CreateListingRequestDTO data, HttpServletRequest request) {
        // get user
        User user = authorizeToken(request);
        Cart cart = cartService.findByUser(user);

        // get listing
        Optional<Listing> getListing = listingServiceBuy.findById(data.getListingId());
        if (getListing.isEmpty()) return ResponseEntity.badRequest().body("Listing with that ID not found");
        Listing listing = getListing.get();

        // create listingInCart
        ListingInCart listingInCart = new ListingInCartBuilder().setCart(cart).setListing(listing).setQuantity(1).build();
        cartService.saveListingInCart(listingInCart);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/cart")
    public ResponseEntity<List<ListingDetailsDto>> getListingInCart(HttpServletRequest request) {
        // get user
        User user = authorizeToken(request);
        //Cart cart = cartService.findByUser(user);

        List<ListingDetailsDto> lic = cartService.findListingsInCartByUser(user);
        return ResponseEntity.ok(lic);
    }





    @PostMapping("/listing/create")
    public ResponseEntity<Object> create(@RequestBody Listing listing) {
        //listingService.create(new Listing("hoho", "url", 12, 123123L, "besar", "baru lah"));
        Listing l = listingServiceBuy.create(listing);

        if (l == null) {
            return new ResponseEntity<>("tidak boleh ada atribut yang null", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(l);
        }
    }

    @PostMapping("/listing/save")
    public ResponseEntity<Object> save(@RequestBody Listing listing) {
        Listing out = listingServiceBuy.save(listing);
        if (out == null) {
            return new ResponseEntity<>("ada masalah, sepertinya karena ada atribut yang null atau mungkin tidak ada listing dengan id " + listing.getId(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(out);
    }

    @DeleteMapping("/listing/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        listingServiceBuy.delete(id);
        return ResponseEntity.ok("successfuly deleted");
    }



}
