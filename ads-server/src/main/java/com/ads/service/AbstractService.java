package com.ads.service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.ads.repository.framework.MyJpaRepository;

public abstract class AbstractService<T> {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected abstract MyJpaRepository<T> getRepository();
    @Transactional(readOnly = false)
    public void delete(Long id) {
        if (id != null && id.longValue() > 0) getRepository().delete(id);
    }
    public T get(Long id) {
        if (id != null && id.longValue() > 0) return getRepository().findOne(id);
        return null;
    }
    public List<T> getAll() {
        return (List<T>) getRepository().findAll();
    }
    @Transactional(readOnly = false)
    public T save(T entity) {
        return getRepository().save(entity);
    }
}
