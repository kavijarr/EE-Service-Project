package dao;

import java.util.List;

public interface CrudDao<T> extends SuperDao{
    Boolean save(T entity);
    Boolean update(T entity);
    Boolean delete(T entity);
    List<T>getAll(T entity);
}
