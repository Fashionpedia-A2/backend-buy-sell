package id.ac.ui.cs.advprog.backendbuysell.repository;
import id.ac.ui.cs.advprog.backendbuysell.model.ListingInCart;
import id.ac.ui.cs.advprog.backendbuysell.model.ListingInCartId;
import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ListingInCartRepository extends JpaRepository<ListingInCart, ListingInCartId> {
    @Query("SELECT lic FROM ListingInCart lic WHERE lic.cart.user = ?1")
    List<ListingInCart> findByUser(User user);
}

