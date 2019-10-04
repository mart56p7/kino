// Mart56p7 01052019
package kano.kino.service;

import kano.kino.model.User;
import kano.kino.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User Service.
 * The class contains
 * - CRUD methods for User.
 * - A validate user method
 * */

@Service("UserService")
public class UserService implements CRUDServiceInterface<User> {

    private UserRepository ur;

    @Autowired
    public UserService(@Qualifier("UserRepositoryMySql") UserRepository ur){
        this.ur = ur;
    }

    /*------------------- CRUD: Start -------------------*/

    public User create(User user) throws SQLException {
        ResultSet rs = ur.createUser(user.getName(), user.getPassword(), user.getUserType().getId());
        user = null;
        if (rs.next()) {
            user = new User(rs.getInt("id"), rs.getString("name"), "", rs.getInt("usertype_id"));
        }
        return user;
    }

    public void edit(User user) throws SQLException {
        ur.editUser(user.getId(), user.getName(), user.getPassword(), user.getUserType().getId());
    }

    public void delete(int id) throws SQLException {
        ur.deleteUser(id);
    }

    public User getId(int id) throws SQLException {
        ResultSet rs = ur.getUser(id);
        User user = null;
        if (rs.next()) {
            user = new User(rs.getInt("id"), rs.getString("name"), "", rs.getInt("usertype_id"));
        }
        return user;
    }

    public List<User> getAll() throws SQLException {
        ResultSet rs = ur.getUsers();
        List<User> users = new ArrayList<>();

        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("name"), "", rs.getInt("usertype_id")));
        }
        return users;
    }

    /*------------------- CRUD: End -------------------*/


}
