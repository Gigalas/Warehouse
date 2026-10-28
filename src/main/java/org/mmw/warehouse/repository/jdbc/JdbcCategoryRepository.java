package org.mmw.warehouse.repository.jdbc;

import org.mmw.warehouse.model.Category;
import org.mmw.warehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

public class JbdcCategoryRepository implements CategoryRepository
{
    
    @Override
    public Optional<Category> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
