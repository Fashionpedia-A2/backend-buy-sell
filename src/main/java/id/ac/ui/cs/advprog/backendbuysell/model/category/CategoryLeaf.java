package id.ac.ui.cs.advprog.backendbuysell.model.category;

import java.util.List;

public class CategoryLeaf extends Category{

    public CategoryLeaf(Long id, String name){
        super(id, name);
        setLeaf(true);
    }

    public void addChild(Category category) {
        throw new IllegalArgumentException();
    }

    public void removeChild(Category category) {
        throw new IllegalArgumentException();
    }

    public List<Category> getChildren() {
        return null;
    }
}
