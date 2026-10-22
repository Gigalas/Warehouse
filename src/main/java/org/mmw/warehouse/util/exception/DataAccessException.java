package org.mmw.warehouse.util.exception;

/**
 * Unchecked exception thrown by the data access layer when a storage operation fails.
 * <p>
 * Repositories wrap low-level errors (for example {@code SQLException}) into this exception,
 * so that the service and UI layers do not need to know anything about SQL.
 */
public class DataAccessException extends RuntimeException
{
    /**
     * Creates an exception with a message.
     *
     * @param message description of what went wrong
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and the original cause.
     *
     * @param message description of what went wrong
     * @param cause   the original error (for example a {@code SQLException}), kept for debugging
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
