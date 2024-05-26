package id.ac.ui.cs.advprog.backendbuysell.service;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.backendbuysell.model.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerService {
    @Autowired
    private UserRepository userRepository;

    public Buyer findById(Long sellerId) {
        Optional<User> result = userRepository.findById(sellerId.intValue());
        if (result.isEmpty()) {
            return null;
        }
        User user = result.get();
        return Buyer
                .builder()
                .id(user.getId().longValue())
                .name(user.getUserProfile().getUserName())
                .address(user.getUserProfile().getAddress())
                .phoneNumber(user.getUserProfile().getPhoneNumber())
                .build();
    }
}
