package dao;

import java.util.List;

public interface CrudDao<T> extends SuperDao{
    Boolean save(T entity);
    Boolean update(T entity);
    Boolean delete(String value);
    List<T>getAll();
}
