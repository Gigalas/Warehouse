package org.mmw.warehouse.model;

import java.util.Objects;

/**
 * Supplier of goods.
 */
public class Supplier
{
    private Long id;
    private String name;
    private String phone;
    private String email;

    public Supplier(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Supplier(Long id, String name, String phone, String email){
        this(name, phone, email);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Supplier other))
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
