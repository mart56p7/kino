package kano.kino.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MovieRepository {
    ResultSet createMovie(String name, String genre, int length) throws SQLException;

    void editMovie(int id, String name, String genre, int length) throws SQLException;

    ResultSet getAllMovies() throws SQLException;

    ResultSet getMovie(int id) throws SQLException;

    void deleteMovie(int id) throws SQLException;
}
