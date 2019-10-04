// Mart56p7 01052019
package kano.kino.repository.mysql;

import kano.kino.repository.Database;
import kano.kino.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * MySql implementation of the UserRepository interface
 *
 * CRUD User operations: Create, Read, Update & Delete on Users.
 * validateUser: Validates a user, based on username and password
 *
 * This class is the basic for validating and controlling users on a webpage or in a CMS.
 * */
@Repository("UserRepositoryMySql")
public class UserRepositoryMySql implements UserRepository {

    private Database dbi;

    @Autowired
    public UserRepositoryMySql(@Qualifier("DatabaseMySql")  Database dbi){
        this.dbi = dbi;
    }

    /**
     * Creates a user
     * The user password is saved as SHA2 512bit, with the salt thats stored in the salt column
     * */
    public ResultSet createUser(String name, String password, int usertype_id) throws SQLException {
        int salt = getSalt();
        String sql = "CALL createUser(?, ?, ?, ?)";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, paddedSalt(salt) + password);
        pstmt.setString(3, paddedSalt(salt));
        pstmt.setInt(4, usertype_id);
        return dbi.query(pstmt);
    }

    private int getSalt(){
        //We want to be sure that we also get the negative numbers, so we see if we are going to return or positive number
        int rint = (int) (Math.random() * 2);
        if(rint == 1){
            return (int)(Math.random() * Integer.MAX_VALUE);
        }
        else{
            return (int)(Math.random() * Integer.MAX_VALUE * -1);
        }
    }

    private String paddedSalt(int salt){
        int missingpad = 11-String.valueOf(salt).length();
        String padding = "";
        for(int i = 0; i < missingpad; i++){
            padding += "_";
        }
        return padding + salt;
    }

    /**
     * Edit a user
     * The user password is saved as SHA2 512bit, with the salt thats stored in the salt column
     * */
    public void editUser(int id, String name, String password, int usertype_id) throws SQLException {
        int salt = getSalt();
        String sql = "UPDATE users SET `name` = ?, `password` = SHA2(?, 512), `salt` = ?, `usertype_id` = ? WHERE `id` = ?";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, paddedSalt(salt) + password);
        pstmt.setString(3, paddedSalt(salt));
        pstmt.setInt(4, usertype_id);
        pstmt.setInt(5, id);
        dbi.execute(pstmt);
    }

    public void deleteUser(int id) throws SQLException{
        String sql = "DELETE FROM  users WHERE id = ?";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        dbi.execute(pstmt);
    }

    public ResultSet getUser(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        return dbi.query(pstmt);
    }

    public ResultSet getUsers() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY `name`";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        return dbi.query(pstmt);
    }

    public ResultSet validateUser(String name, String password) throws SQLException {
        System.out.println("Validate user");
        String sql = "CALL validateUser(?, ?)";
        PreparedStatement pstmt = dbi.getConnection().prepareCall(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, password);
        return dbi.query(pstmt);
    }
}
