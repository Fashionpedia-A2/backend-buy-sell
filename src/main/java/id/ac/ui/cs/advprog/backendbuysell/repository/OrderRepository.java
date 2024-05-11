package id.ac.ui.cs.advprog.backendbuysell.repository;

import id.ac.ui.cs.advprog.backendbuysell.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Listing, Long> {
}
