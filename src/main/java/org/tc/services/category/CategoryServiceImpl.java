package org.tc.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.category.CategoryDao;
import org.tc.models.Category;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void create(Category newCategory) {
        categoryDao.create(newCategory);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryDao.getCategoryByName(categoryName);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryDao.getByCategoryId(id);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }
}
