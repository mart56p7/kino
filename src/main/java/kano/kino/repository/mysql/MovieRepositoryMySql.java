// Mart56p7 01052019
package kano.kino.repository.mysql;

import kano.kino.repository.Database;
import kano.kino.repository.MovieRepository;
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
@Repository("MovieRepositoryMySql")
public class MovieRepositoryMySql implements MovieRepository {

    private Database dbi;

    @Autowired
    public MovieRepositoryMySql(@Qualifier("DatabaseMySql")  Database dbi){
        this.dbi = dbi;
    }

    /**
     * Creates a user
     * The user password is saved as SHA2 512bit, with the salt thats stored in the salt column
     * */
    @Override
    public ResultSet createMovie(String name, String genre, int length) throws SQLException {
        String sql = "CALL createMovie(?, ?, ?)";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, genre);
        pstmt.setInt(3, length);
        return dbi.query(pstmt);
    }

    /**
     * Edit a user
     * The user password is saved as SHA2 512bit, with the salt thats stored in the salt column
     * */
    @Override
    public void editMovie(int id, String name, String genre, int length) throws SQLException {
        //hvor vi er noget til!
        String sql = "UPDATE movies SET `name` = ?, `genre` = ?, `length` = ? WHERE `id` = ?";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, genre);
        pstmt.setInt(3, length);
        pstmt.setInt(4, id);
        dbi.execute(pstmt);
    }

    @Override
    public ResultSet getAllMovies() throws SQLException {
        String sql = "SELECT * FROM movies ORDER BY `name`";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        return dbi.query(pstmt);
    }

    @Override
    public ResultSet getMovie(int id) throws SQLException {
        String sql = "SELECT * FROM movies WHERE id=?";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        return dbi.query(pstmt);
    }

    public void deleteMovie(int id) throws SQLException{
        String sql = "DELETE FROM  movies WHERE id = ?";
        PreparedStatement pstmt = dbi.getConnection().prepareStatement(sql);
        pstmt.setInt(1, id);
        dbi.execute(pstmt);
    }
}
