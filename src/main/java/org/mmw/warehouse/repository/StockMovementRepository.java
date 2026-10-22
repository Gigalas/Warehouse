package org.mmw.warehouse.repository;

import org.mmw.warehouse.model.Product;
import org.mmw.warehouse.model.StockMovement;
import org.mmw.warehouse.util.exception.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data access contract for {@link StockMovement}, the journal of the warehouse.
 * <p>
 * The journal is append-only: there are no methods to update or delete an entry.
 * A mistake is corrected with a new compensating movement.
 * <p>
 * Any method may throw {@link DataAccessException} if the storage fails.
 */
public interface StockMovementRepository
{

    /**
     * Registers a movement atomically: in a single transaction it changes the stock
     * of the product (increases it for {@code IN}, decreases it for {@code OUT})
     * and inserts the journal entry. If any step fails, nothing is saved.
     * <p>
     * The unit price and the creation time are taken from the given movement as they are.
     *
     * @param movement the movement to register; its product must already be saved
     * @return the saved movement containing the generated id
     * @throws DataAccessException if the product does not exist, there is not enough stock
     *                             for an {@code OUT} movement, a supplier is set for
     *                             an {@code OUT} movement, or the storage fails
     */
    StockMovement register(StockMovement movement);

    /**
     * Returns the whole journal, newest entries first.
     *
     * @return the list of movements; never {@code null}, empty if there are none
     */
    List<StockMovement> findAll();

    /**
     * Returns the movements of one product, newest first.
     *
     * @param productId the id of the product
     * @return the list of movements; never {@code null}
     */
    List<StockMovement> findByProduct(long productId);

    /**
     * Returns the movements created in the given period, newest first.
     *
     * @param from the start of the period (inclusive)
     * @param to   the end of the period (exclusive)
     * @return the list of movements; never {@code null}
     */
    List<StockMovement> findBetween(LocalDateTime from, LocalDateTime to);
}
