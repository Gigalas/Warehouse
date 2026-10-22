package org.mmw.warehouse.repository;

import org.mmw.warehouse.model.Category;
import org.mmw.warehouse.util.exception.DataAccessException;

import java.util.List;
import java.util.Optional;

/**
 * Data access contract for {@link Category}.
 * <p>
 * Implementations hide all storage details (SQL, JDBC) from the rest of the application.
 * Any method may throw {@link DataAccessException} if the storage fails.
 */
public interface CategoryRepository
{
    /**
     * Finds a category by its id.
     *
     * @param id the category id
     * @return the category, or an empty {@link Optional} if no category has this id
     */
    Optional<Category> findById(long id);

    /**
     * Finds a category by its unique name.
     *
     * @param name the category name
     * @return the category, or an empty {@link Optional} if there is no such category
     */
    Optional<Category> findByName(String name);

    /**
     * Returns all categories sorted by name.
     *
     * @return the list of categories; never {@code null}, empty if there are none
     */
    List<Category> findAll();

    /**
     * Saves a category: inserts it if its id is {@code null}, updates it otherwise.
     *
     * @param category the category to save
     * @return the saved category; after an insert it contains the generated id
     * @throws DataAccessException if the name is already used by another category
     *                             or the storage fails
     */
    Category save(Category category);

    /**
     * Deletes a category by its id.
     *
     * @param id the category id
     * @throws DataAccessException if the category is still used by products
     *                             or the storage fails
     */
    void deleteById(long id);
}
