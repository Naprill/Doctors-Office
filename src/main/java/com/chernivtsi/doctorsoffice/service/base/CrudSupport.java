package com.chernivtsi.doctorsoffice.service.base;

import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;

import java.util.List;
import java.util.Optional;

/**
 * Defines commonly used CRUD operations
 */
public interface CrudSupport <E extends AbstractIdentifiable> {

    /**
     * Retrieves entity by it's unique identifier
     *
     * @param entityId entity identifier
     * @return optional of entity
     */
    Optional<E> findById(final Long entityId);

    /**
     * Retrieves all entities
     *
     * @return list of entities
     */
    List<E> findAll();

    /**
     * Updates an entity.
     * If this entity hasn't persisted yet, {@link IllegalArgumentException}
     * will be thrown, so if you want to use one
     * method for <strong>create</strong> and <strong>update</strong>
     * operations use {@link #save(AbstractIdentifiable)} instead
     *
     * @param entity entity to update
     * @return updated entity
     * @throws IllegalArgumentException if entity hasn't persisted yet
     */
    E update(final E entity);

    /**
     * Persists an entity.
     * If entity has already persisted, {@link IllegalArgumentException} will
     * be thrown
     *
     * @param entity entity to create
     * @return created entity
     * @throws IllegalArgumentException if entity has already persisted
     */
    E create(final E entity);

    /**
     * Saves an entity.
     * This method resolves entity's state and
     * delegates to either {@link #create(AbstractIdentifiable)} or {@link #update(AbstractIdentifiable)}
     * method to do actual work
     *
     * @param entity entity to save
     * @return saved entity
     */
    E save(final E entity);

    /**
     * Removes an entity
     * If entity hasn't persisted {@link IllegalArgumentException} will be thrown
     *
     * @param entity entity to remove
     * @throws IllegalArgumentException if entity hasn't persisted yet
     */
    void delete(final E entity);

    /**
     * Removes an entity by it's identifier
     *
     * @param id unique identifier
     */
    void delete(final Long id);
}
