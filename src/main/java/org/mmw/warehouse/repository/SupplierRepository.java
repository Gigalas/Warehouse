package org.mmw.warehouse.repository;

import org.mmw.warehouse.model.Supplier;
import org.mmw.warehouse.util.exception.DataAccessException;

import java.util.Optional;
import java.util.List;

/**
 * Data access contract for {@link Supplier}.
 * <p>
 * Implementations hide all storage details (SQL, JDBC) from the rest of the application.
 * Any method may throw {@link DataAccessException} if the storage fails.
 */
public interface SupplierRepository
{
    /**
     * Finds a supplier by its id.
     *
     * @param id the supplier id
     * @return the supplier, or an empty {@link Optional} if no supplier has this id
     */
    Optional<Supplier> findById(long id);

    /**
     * Returns all suppliers sorted by name.
     *
     * @return the list of suppliers; never {@code null}, empty if there are none
     */
    List<Supplier> findAll();

    /**
     * Saves a supplier: inserts it if its id is {@code null}, updates it otherwise.
     *
     * @param supplier the supplier to save
     * @return the saved supplier; after an insert it contains the generated id
     * @throws DataAccessException if the storage fails
     */
    Supplier save(Supplier supplier);

    /**
     * Deletes a supplier by its id.
     *
     * @param id the supplier id
     * @throws DataAccessException if the supplier is still referenced by stock movements
     *                             or the storage fails
     */
    void deleteById(long id);
}
