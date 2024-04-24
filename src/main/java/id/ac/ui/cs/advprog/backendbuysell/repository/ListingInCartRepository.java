package id.ac.ui.cs.advprog.backendbuysell.repository;
import id.ac.ui.cs.advprog.backendbuysell.model.ListingInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ListingInCartRepository extends JpaRepository<ListingInCart, Long> {
}

