package id.ac.ui.cs.advprog.backendbuysell.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "listing")
@Getter
@Setter
public class Listing {

    @Id
    @GeneratedValue
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name="category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "price")
    private Long price;

    @Column(name = "size")
    private String size;

    @Column(name = "condition")
    private String condition;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    public void setStock(int stock) {
        if(stock < 0) {
            throw new IllegalArgumentException("Stock must be non-negative number");
        }
        this.stock = stock;
    }

    public void setPrice(Long price) {
        if(price < 0L) {
            throw new IllegalArgumentException("Price must be non-negative number");
        }
        this.price = price;
    }
}