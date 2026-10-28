package org.mmw.warehouse.repository.jdbc;

import org.mmw.warehouse.model.Category;
import org.mmw.warehouse.repository.CategoryRepository;
import org.mmw.warehouse.util.data.DbConnection;
import org.mmw.warehouse.util.exception.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCategoryRepository implements CategoryRepository
{
    private final DbConnection db;

    public JdbcCategoryRepository(DbConnection db) {
        this.db = db;
    }

    @Override
    public Optional<Category> findById(long id)
    {
        String sql = "SELECT id, name, description FROM categories WHERE id = ?";
        try(Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery())
            {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to load category with id " + id, e);
        }
    }

    @Override
    public Optional<Category> findByName(String name)
    {
        String sql = "SELECT id, name, description FROM categories WHERE name = ?";
        try(Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e){
            throw new DataAccessException("Failed to load category with name " + name, e);
        }
    }

    @Override
    public List<Category> findAll()
    {
        String sql = "SELECT id, name, description FROM categories ORDER BY name COLLATE NOCASE";
        List<Category> result = new ArrayList<>();
        try(Connection con = db.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql))
        {
            while (rs.next()){
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e){
            throw new DataAccessException("Failed to load categories", e);
        }
    }

    @Override
    public Category save(Category category) {
        try {
            if (category.getId() == null){
                insert(category);
            } else {
                update(category);
            }
            return category;
        } catch (SQLException e) {
            if (hasMessage(e, "unique constraint failed")) {
                throw new DataAccessException("Category name already exists: " + category.getName(), e);
            }
            throw new DataAccessException("Failed to save category", e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            if (hasMessage(e, "foreign key constraint failed")) {
                throw new DataAccessException("Category is still used by products: " + id, e);
            }
            throw new DataAccessException("Failed to delete category " + id, e);
        }
    }

    private void insert(Category category) throws SQLException {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";
        try(Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys())
            {
                if (!keys.next()) {
                    throw new DataAccessException("The database did not return an id for new category");
                }
                category.setId(keys.getLong(1));
            }
        }
    }

    private void update(Category category) throws SQLException {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
        try(Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setLong(3, category.getId());
            if(ps.executeUpdate() == 0){
                throw new DataAccessException("Category not found: " + category.getId());
            }
        }
    }

    private Category mapRow(ResultSet rs) throws SQLException
    {
        return new Category(rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"));
    }

    private boolean hasMessage(SQLException e, String text)
    {
        return e.getMessage() != null && e.getMessage().toLowerCase().contains(text);
    }
}
