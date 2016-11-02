package org.tc.dao.category;

import org.tc.models.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getAll();

    Category getCategoryByName(String categoryName);
}
