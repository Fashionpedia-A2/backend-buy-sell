package id.ac.ui.cs.advprog.backendbuysell.model.category;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CategoryComposite extends Category {
    public CategoryComposite(Long id, String name){
        super(id, name);
        setLeaf(false);
        this.children = new ArrayList<>();
    }
    public void addChild(Category category) {
        this.children.add(category);
    }

    public void removeChild(Category category) {
        boolean isRemoved = this.children.remove(category);
        if(!isRemoved){
            throw new NoSuchElementException();
        }
    }

    public List<Category> getChildren() {
        return this.children;
    }
}
