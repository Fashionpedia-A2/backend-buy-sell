package id.ac.ui.cs.advprog.backendbuysell.repository;

import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
}