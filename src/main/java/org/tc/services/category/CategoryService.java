package org.tc.services.category;

import org.tc.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getCategoryByName(String categoryName);
}
