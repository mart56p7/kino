package kano.kino.service;

import java.sql.SQLException;
import java.util.List;

/**
 * Default CRUD interface for services.
 *
 * create: Create
 * edit: Edit/Update
 * delete: Delete
 * getId: Get 1 item
 * getAll: Get all item(s)
 * */

public interface CRUDServiceInterface<E> {
    E create(E e) throws SQLException;
    void edit(E e) throws SQLException;
    void delete(int id) throws SQLException;
    E getId(int id) throws SQLException;
    List<E> getAll() throws SQLException;
}
