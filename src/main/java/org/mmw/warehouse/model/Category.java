package org.mmw.warehouse.model;

import java.util.Objects;

/**
 * Product category (for example, “Electronics”, “Stationery”).
 */
public class Category
{
    private Long id;
    private String name;
    private String description;

    public Category(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public Category(Long id, String name, String description) {
        this(name, description);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Category other))
            return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
