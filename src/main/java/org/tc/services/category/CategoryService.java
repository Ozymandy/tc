package org.tc.services.category;

import org.tc.models.Category;

import java.util.List;

public interface CategoryService {
    void create(Category newCategory);

    List<Category> getAll();

    Category getCategoryByName(String categoryName);

    Category getCategoryById(int id);

    void delete(Category category);
}
