package id.ac.ui.cs.advprog.backendbuysell.controller;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.auth.service.JwtService;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.dto.SellerDetailsDto;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import id.ac.ui.cs.advprog.backendbuysell.service.ListingServiceBuy;
import id.ac.ui.cs.advprog.backendbuysell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/buyer")
public class BuyController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ListingServiceBuy listingServiceBuy;

    @Autowired
    private SellerService sellerService;


    @GetMapping("/protected")
    public String protectedEndpoint(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = header.substring(7);

        User user = jwtService.extractUser(token);
        if (!jwtService.isTokenValid(token, user)) {
            throw new RuntimeException("Invalid token");
        }

        return "Hello, you accessed a protected endpoint!";
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
