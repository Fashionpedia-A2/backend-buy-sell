package id.ac.ui.cs.advprog.backendbuysell.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Buyer {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
