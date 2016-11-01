package org.tc.dao.category;

import org.tc.models.Category;

import java.util.List;

public interface CategoryDao {
    void create(Category newCategory);

    List<Category> getAll();

    Category getCategoryByName(String categoryName);

    Category getByCategoryId(int id);

    void delete(Category category);
}
