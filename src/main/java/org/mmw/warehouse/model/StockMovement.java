package org.mmw.warehouse.model;

import org.mmw.warehouse.model.enums.MovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A journal entry: incoming or outgoing goods.
 * <p>
 * Entries are only ever added, never edited: this is the history of the warehouse
 * The unit price is a snapshot taken at the moment of the movement, so later
 * price change of  the product do not affect historical report
 */
public class StockMovement
{
    private Long id;
    private Product product;
    private Supplier supplier;
    private MovementType type;
    private int quantity;
    private BigDecimal unitPrice;
    private LocalDateTime createdAt;
    private String comment;

    public StockMovement(Product product, Supplier supplier, MovementType type,
                         int quantity, BigDecimal unitPrice,
                         LocalDateTime createdAt, String comment)
    {
        this.product = product;
        this.supplier = supplier;
        this.type = type;

    }

    /** Total value of this movement: unit price * quantity. */
    public BigDecimal getTotalValue() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Movement quantity must be greater than zero");
        }
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.signum() < 0) {
            throw new IllegalArgumentException("Unit price must not be null or negative");
        }
        this.unitPrice = unitPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof StockMovement other))
            return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return type + " " + quantity + " x " + product + " (" + createdAt + ")";
    }
}
