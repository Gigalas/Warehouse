package org.mmw.warehouse.repository;

import org.mmw.warehouse.model.Category;
import org.mmw.warehouse.model.Product;
import org.mmw.warehouse.util.exception.DataAccessException;

import java.util.List;
import java.util.Optional;


/**
 * Data access contract for {@link Product}.
 * <p>
 * Products are never physically deleted: they are deactivated, because the journal
 * of stock movements refers to them. The list methods return active products only,
 * while the {@code find...} methods for a single product also find deactivated ones.
 * <p>
 * The stock quantity is never changed here; it changes only through
 * {@link StockMovementRepository#register}.
 * <p>
 * Any method may throw {@link DataAccessException} if the storage fails.
 */
public interface ProductRepository
{
    /**
     * Finds a product by its id, including deactivated products.
     *
     * @param id the product id
     * @return the product, or an empty {@link Optional} if no product has this id
     */
    Optional<Product> findById(long id);

    /**
     * Finds a product by its unique SKU (article number), including deactivated products.
     *
     * @param sku the article number
     * @return the product, or an empty {@link Optional} if there is no such product
     */
    Optional<Product> findBySku(String sku);

    /**
     * Returns all active products sorted by name.
     *
     * @return the list of active products; never {@code null}, empty if there are none
     */
    List<Product> findAll();

    /**
     * Searches active products whose name or SKU contains the given text, ignoring case.
     *
     * @param text the text to look for
     * @return the matching products sorted by name; never {@code null}
     */
    List<Product> search(String text);

    /**
     * Returns all active products of the given category, sorted by name.
     *
     * @param categoryId the id of the category
     * @return the list of products; never {@code null}
     */
    List<Product> findByCategory(long categoryId);

    /**
     * Returns all active products whose stock is at or below their minimum stock level.
     *
     * @return the list of low-stock products sorted by name; never {@code null}
     */
    List<Product> findLowStock();

    /**
     * Saves a product: inserts it if its id is {@code null}, updates it otherwise.
     * <p>
     * The stock quantity is not touched by this method: a new product is always stored
     * with quantity 0 and the quantity of an existing product is never updated.
     * Stock changes only through {@link StockMovementRepository#register}.
     *
     * @param product the product to save
     * @return the saved product; after an insert it contains the generated id
     * @throws DataAccessException if the SKU is already used by another product,
     *                             the category does not exist or the storage fails
     */
    Product save(Product product);

    /**
     * Deactivates a product (soft delete). Its movement history is kept.
     *
     * @param id the product id
     * @throws DataAccessException if the storage fails
     */
    void deactivate(long id);
}
