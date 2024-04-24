package id.ac.ui.cs.advprog.backendbuysell.model;

import id.ac.ui.cs.advprog.backendbuysell.model.category.Category;
import id.ac.ui.cs.advprog.backendbuysell.model.category.CategoryComposite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
    List<Category> categoryComposites;
    List<Category> categoryLeaves;

    @BeforeEach
    void setUp() {
        this.categoryComposites = new ArrayList<>();
        categoryComposites.add(new CategoryComposite(0L, "Atasan"));
        categoryComposites.add(new CategoryComposite(1L, "Baju Pria"));
        categoryComposites.add(new CategoryComposite(2L, "Baju Wanita"));

        this.categoryLeaves = new ArrayList<>();
        categoryLeaves.add(new CategoryComposite(3L, "Kemeja Pria"));
        categoryLeaves.add(new CategoryComposite(4L, "Kaos Pria"));
        categoryLeaves.add(new CategoryComposite(5L, "Outer Wanita"));
        categoryLeaves.add(new CategoryComposite(6L, "Rompi Wanita"));
    }

    @Test
    void testIsLeafCategoryComposite() {
        Category composite = categoryComposites.getFirst();
        assertFalse(composite.isLeaf());
    }

    @Test
    void testIsLeafCategoryLeaf() {
        Category leaf = categoryLeaves.getFirst();
        assertTrue(leaf.isLeaf());
    }

    @Test
    void testGetEmptyChildrenOnCategoryComposite() {
        Category parent = categoryComposites.getFirst();
        assertEquals(0, parent.getChildren().size());
    }

    @Test
    void testAddChildOnCategoryComposite() {
        Category parent = categoryComposites.getFirst();
        Category child = categoryComposites.get(1);

        parent.addChild(child);
        assertEquals(1, parent.getChildren().size());
    }


    @Test
    void testAddChildOnCategoryLeaf() {
        Category parent = categoryLeaves.getFirst();
        Category child = categoryComposites.get(1);

        assertThrows(IllegalArgumentException.class, () -> {
            parent.addChild(child);
        });
    }

    @Test
    void testRemoveChildFromCategoryComposite() {
        Category parent = categoryComposites.getFirst();
        Category child = categoryComposites.get(1);

        parent.addChild(child);
        parent.removeChild(child);

        assertEquals(0, parent.getChildren().size());
    }

    @Test
    void testRemoveNonExistingChildFromCategoryComposite() {
        Category parent = categoryComposites.getFirst();
        Category child = categoryComposites.get(1);

        assertThrows(NoSuchElementException.class, () -> {
            parent.removeChild(child);
        });
    }
}
