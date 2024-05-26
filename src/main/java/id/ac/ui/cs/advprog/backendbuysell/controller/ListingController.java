package id.ac.ui.cs.advprog.backendbuysell.controller;

import id.ac.ui.cs.advprog.backendbuysell.dto.ApiResponse;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListRequestDTO;
import id.ac.ui.cs.advprog.backendbuysell.dto.ListingListResponseDTO;
import id.ac.ui.cs.advprog.backendbuysell.exception.FieldValidationException;
import id.ac.ui.cs.advprog.backendbuysell.exception.ForbiddenException;
import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import id.ac.ui.cs.advprog.backendbuysell.service.ListingService;
import id.ac.ui.cs.advprog.backendbuysell.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@EnableWebMvc
public class ListingController {
    @Autowired
    ListingService listingService;

    @Autowired
    JwtHelper jwtHelper;

    @CrossOrigin
    @GetMapping("/listing")
    public ResponseEntity<ApiResponse<ListingListResponseDTO>> getAllListings(
            ListingListRequestDTO request,
            @PageableDefault(page = 0, size = 40) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        request.setPageable(pageable);
        ListingListResponseDTO result = listingService.getAll(request);
        ApiResponse<ListingListResponseDTO> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("/buyer/listing-buyer")
    public ResponseEntity<ApiResponse<ListingListResponseDTO>> getBuyerViewListings(
            ListingListRequestDTO request,
            @PageableDefault(page = 0, size = 40) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        request.setPageable(pageable);
        ListingListResponseDTO result = listingService.getActiveListings(request);
        ApiResponse<ListingListResponseDTO> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("/seller/listing")
    public ResponseEntity<ApiResponse<ListingListResponseDTO>> getSellerListings(
            ListingListRequestDTO request,
            @PageableDefault(page = 0, size = 40) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestHeader("Authorization") String token) {
        Long sellerId = jwtHelper.getUserIdFromToken(token);
        request.setPageable(pageable);
        ListingListResponseDTO result = listingService.getSellerListings(sellerId, request);
        ApiResponse<ListingListResponseDTO> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("/listing/{id}")
    public ResponseEntity<ApiResponse<Listing>> getListingById(@PathVariable Long id) {
        Optional<Listing> result = listingService.getById(id);
        ApiResponse<Listing> response;
        if (result.isPresent()) {
            response = ApiResponse.success(result.get());
            return ResponseEntity.ok(response);
        } else {
            response = ApiResponse.failed("Listing with id " + id + " does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @CrossOrigin
    @PostMapping("/seller/listing")
    public ResponseEntity<ApiResponse<Listing>> createListing(
            @RequestBody Listing listing,  @RequestHeader("Authorization") String token) {
        ApiResponse<Listing> response;
        try {
            Long sellerId = jwtHelper.getUserIdFromToken(token);
            Listing createdListing = listingService.create(listing, sellerId);
            response = ApiResponse.success(createdListing, "Listing created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (FieldValidationException e){
            List<String> errors = e.getErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage).toList();
            response = ApiResponse.failed(errors, "Validation failed");
            return ResponseEntity.badRequest().body(response);
        } catch (ForbiddenException e){
            response = ApiResponse.failed(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @CrossOrigin
    @PutMapping("/seller/listing/{id}")
    public ResponseEntity<ApiResponse<Listing>> updateListing(
            @PathVariable Long id, @RequestBody Listing newListing,
            @RequestHeader("Authorization") String token) {
        ApiResponse<Listing> response;
        try {
            Long sellerId = jwtHelper.getUserIdFromToken(token);
            Listing savedListing = listingService.update(id, newListing, sellerId);
            response = ApiResponse.success(savedListing, "Listing updated successfully.");
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            response = ApiResponse.failed("Listing with id " + id + " does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (FieldValidationException e){
            List<String> errors = e.getErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage).toList();
            response = ApiResponse.failed(errors, "Validation failed");
            return ResponseEntity.badRequest().body(response);
        } catch (ForbiddenException e){
            response = ApiResponse.failed(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @CrossOrigin
    @DeleteMapping("/seller/listing/{id}")
    public ResponseEntity<ApiResponse<Listing>> deleteListing(
            @PathVariable Long id, @RequestHeader("Authorization") String token) {
        ApiResponse<Listing> response;
        try {
            Long sellerId = jwtHelper.getUserIdFromToken(token);
            Listing deletedListing = listingService.delete(id, sellerId);
            response = ApiResponse.success(deletedListing, "Listing deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            response = ApiResponse.failed("Listing with id " + id + " does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (FieldValidationException e){
            List<String> errors = e.getErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage).toList();
            response = ApiResponse.failed(errors, "Validation failed");
            return ResponseEntity.badRequest().body(response);
        } catch (ForbiddenException e){
            response = ApiResponse.failed(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}
