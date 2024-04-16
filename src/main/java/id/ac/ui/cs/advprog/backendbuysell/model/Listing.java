package id.ac.ui.cs.advprog.backendbuysell.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="listing")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "stock")
    private int stock;

    @Column(name = "size")
    private String size;

    @Column(name = "condition")
    private String condition;

    @Column(name = "description")
    private String description;

    public Listing(String id, String name, String imageUrl, int stock, String size, String condition, String description){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.size = size;
        this.condition = condition;
        this.description = description;
    }
}
