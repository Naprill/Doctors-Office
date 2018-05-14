package com.chernivtsi.doctorsoffice.service.base;

import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Default implementation of {@link CrudSupport} which simply delegates
 * crud operations to {@link CrudRepository}
 */
@AllArgsConstructor
public abstract class DefaultCrudSupport<E extends AbstractIdentifiable> implements CrudSupport<E> {

    private CrudRepository<E, Long> repository;

    @Override
    public Optional<E> findById(Long entityId) {
        return Optional.ofNullable(repository.findOne(entityId));
    }

    @Override
    public List<E> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public E update(E entity) {
        checkArgument(nonNull(entity.getId()),
                "Could not update entity. Entity hasn't persisted yet");
        return repository.save(entity);
    }

    @Override
    public E create(E entity) {
        checkArgument(isNull(entity.getId()),
                "Could not create entity. Entity has already exists");
        return repository.save(entity);
    }

    @Override
    public E save(E entity) {
       return isNull(entity.getId()) ? create(entity) : update(entity);
    }

    @Override
    public void delete(E entity) {
        checkArgument(nonNull(entity.getId()),
                "Could not delete entity. Entity hasn't persisted yet");
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
