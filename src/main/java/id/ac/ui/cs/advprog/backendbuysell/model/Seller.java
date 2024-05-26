package id.ac.ui.cs.advprog.backendbuysell.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.List;

@Setter
@Getter
public class Seller {

    private Long id;

    private String name;

    private List<Listing> listing;
}
//@Entity
//@Table(name="seller")
//@Getter
//@Immutable
//public class Seller {
//    @Setter
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Setter
//    private String name;
//
//    @OneToMany(mappedBy = "sellerId")
//    private List<Listing> listing;
//    @Override
//    public String toString() {
//        ObjectMapper om = new ObjectMapper();
//        try {
//            return om.writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}