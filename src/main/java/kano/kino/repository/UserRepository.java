// Mart56p7 01052019
package kano.kino.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Repository interface for the UserRepository.
 * A specific database implementation for the UserRepository, needs to implement this interface
 * */

public interface UserRepository {
    ResultSet createUser(String name, String password, int usertype_id) throws SQLException;
    void editUser(int id, String name, String password, int usertype_id) throws SQLException;
    void deleteUser(int id) throws SQLException;
    ResultSet getUser(int id) throws SQLException;
    ResultSet getUsers() throws SQLException;
    ResultSet validateUser(String name, String password) throws SQLException;
}
