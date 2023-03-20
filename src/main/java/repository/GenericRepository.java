package repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository <T,ID>{
    void create(T t) throws SQLException;

    T get(ID id) throws SQLException;

    List<T> getAll() throws SQLException;

    boolean delete(ID id) throws SQLException;

    int update(T object) throws SQLException;
}
