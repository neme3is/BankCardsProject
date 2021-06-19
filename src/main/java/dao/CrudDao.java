package dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> {
    T get(Long id) throws Exception;

    List<T> getAll() throws SQLException;

    T update(Long id) throws SQLException;

    void create(T something) throws SQLException;

    T delete(Long id) throws SQLException;
}
