package id.ac.ui.cs.advprog.backendbuysell.repository;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {
    @Query("SELECT L FROM Listing L WHERE L.sellerId = :sellerId")
    List<Listing> findAllBySellerId(@Param("sellerId") String sellerId);

    @Query("SELECT L FROM Listing L WHERE L.name ILIKE %:name%")
    List<Listing> findAllByName(@Param("name") String name);
}
