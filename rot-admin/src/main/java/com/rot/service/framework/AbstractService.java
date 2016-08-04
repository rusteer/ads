package com.rot.service.framework;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.rot.entity.framework.IdEntity;

public abstract class AbstractService<T extends IdEntity> {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
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
    public Map<Long, T> getMapAll() {
        Map<Long, T> map = new HashMap<Long, T>();
        for (T t : getAll()) {
            map.put(t.getId(), t);
        }
        return map;
    }
    protected abstract CrudRepository<T, Long> getRepository();
    @Transactional(readOnly = false)
    public T save(T entity) {
        T t = getRepository().save(entity);
        return t;
    }
}
