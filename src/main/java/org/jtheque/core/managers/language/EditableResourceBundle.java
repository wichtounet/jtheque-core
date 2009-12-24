package org.jtheque.core.managers.language;

/**
 * An editable resource bundle.
 *
 * @author Baptiste Wicht
 */
public interface EditableResourceBundle {
    /**
     * Add base name.
     *
     * @param baseName The base name to add.
     */
    void addBaseName(String baseName);

    /**
     * Remove a base name.
     *
     * @param baseName The base name.
     */
    void removeBaseName(String baseName);
}
