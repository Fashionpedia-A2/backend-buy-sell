package id.ac.ui.cs.advprog.backendbuysell.model.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Category {
    protected Long id;
    protected String name;
    protected List<Category> children;
    protected boolean isLeaf;

    public Category(Long id, String name){
        setId(id);
        setName(name);
    }

    public abstract void addChild(Category category);
    public abstract void removeChild(Category category);
    public abstract List<Category> getChildren();
}
