package id.ac.ui.cs.advprog.backendbuysell.auth.model;

import jakarta.persistence.*;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile {

    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String address;

    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String about;


}