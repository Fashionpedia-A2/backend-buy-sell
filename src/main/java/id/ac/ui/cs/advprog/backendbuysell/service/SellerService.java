package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.backendbuysell.model.Seller;
import id.ac.ui.cs.advprog.backendbuysell.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private UserRepository userRepository;

    public Seller findById(Long sellerId) {
        Optional<User> result = userRepository.findById(sellerId.intValue());
        if (result.isEmpty()) {
            return null;
        }
        User user = result.get();
        Seller seller = new Seller();
        seller.setId(user.getId().longValue());
        seller.setName(user.getUserProfile().getUserName());
        return seller;
    }
}