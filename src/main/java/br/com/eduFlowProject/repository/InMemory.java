package br.com.eduFlowProject.repository;

import java.util.*;

public abstract class InMemory<T, ID> implements Repository<T, ID>{

    private final Map<ID, T> storage = new HashMap<>();

    @Override
    public T save(T entity) {
        storage.put(getId(entity), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(ID id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return storage.containsKey(id);
    }

    protected abstract ID getId(T entity);
}
