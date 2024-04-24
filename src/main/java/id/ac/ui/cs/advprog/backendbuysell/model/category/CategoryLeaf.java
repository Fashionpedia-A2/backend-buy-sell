package id.ac.ui.cs.advprog.backendbuysell.model.category;

import java.util.List;

public class CategoryLeaf extends Category{

    public CategoryLeaf(Long id, String name){
        super(id, name);
    }

    public void addChild(Category category) {
    }

    public void removeChild(Category category) {
    }

    public List<Category> getChildren() {
        return null;
    }
}
