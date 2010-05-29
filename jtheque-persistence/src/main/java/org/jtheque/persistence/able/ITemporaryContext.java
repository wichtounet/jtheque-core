package org.jtheque.persistence.able;

/**
 * A temporary context for an entity.
 *
 * @author Baptiste Wicht
 */
public interface ITemporaryContext {
    /**
     * Return the id of the context.
     *
     * @return The id.
     */
    int getId();

    /**
     * Set the id of the context.
     *
     * @param id The new id to set.
     */
    void setId(int id);
}
