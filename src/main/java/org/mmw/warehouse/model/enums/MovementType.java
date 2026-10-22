package org.mmw.warehouse.model.enums;

/**
 * Type of a stock movement.
 */
public enum MovementType
{
    // Incoming goods: the stock increases.
    IN("Incoming"),
    // Outgoing goods: the stock decreases.
    OUT("Outgoing");

    private final String title;

    MovementType(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public String toString(){
        return title;
    }
}
