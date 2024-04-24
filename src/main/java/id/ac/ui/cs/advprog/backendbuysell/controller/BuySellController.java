package id.ac.ui.cs.advprog.backendbuysell.controller;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/buyer")
public class BuySellController {

    @Autowired
    private ListingService listingService;
    @RequestMapping("")
    public String main(){
        return "uglyhtml";
    }

    @GetMapping("/listing")
    public ResponseEntity<List<Listing>> getAllListing() {
        List<Listing> list = listingService.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/listing/create")
    public ResponseEntity<Object> create(@RequestBody Listing listing) {
        //listingService.create(new Listing("hoho", "url", 12, 123123L, "besar", "baru lah"));
        Listing l = listingService.create(listing);

        if (l == null) {
            return new ResponseEntity<>("tidak boleh ada atribut yang null", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(l);
        }


    }

    @PostMapping("/listing/save")
    public ResponseEntity<Object> save(@RequestBody Listing listing) {
        Listing out = listingService.save(listing);
        if (out == null) {
            return new ResponseEntity<>("ada masalah, sepertinya karena ada atribut yang null atau mungkin tidak ada listing dengan id " + listing.getId(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(out);
    }

    @DeleteMapping("/listing/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        listingService.delete(id);
        return ResponseEntity.ok("successfuly deleted");
    }
}
