package org.mmw.warehouse.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Product in stock.
 */
public class Product
{
    private Long id;
    private String sku;
    private String name;
    private Category category;
    private BigDecimal price;
    private int quantity;
    private String unit;
    private int minQuantity;
    private boolean active = true;

    public Product(String sku, String name, Category category, BigDecimal price, int quantity, String unit, int minQuantity){
        this.sku = sku;
        this.name = name;
        this.category = category;
        setPrice(price);
        setQuantity(quantity);
        this.unit = unit;
        setMinQuantity(minQuantity);
    }

    /**
     * Checks whether the product stock has reached or fallen below the minimum level.
     * @return true if the product stock is low, otherwise false
     */
    public boolean isLowStock(){
        return quantity <= minQuantity;
    }

    /**
     * Calculates the total value of all items in stock.
     *
     * @return the total value of the items
     */
    public BigDecimal getTotalValue() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if(price == null || price.signum() < 0){
            throw new IllegalArgumentException("The price cannot be empty or negative");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The remainder cannot be negative.");
        }
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        if (minQuantity < 0){
            throw new IllegalArgumentException("The minimum balance cannot be negative.");
        }
        this.minQuantity = minQuantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Product other))
            return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return sku + " - " + name;
    }
}
