package kano.kino.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The database interface expect that you defined the following constraints in application.properties
 * kino.datasource.port = The port for the database
 * kino.datasource.username = The database user
 * kino.datasource.password = The password for the database user
 * kino.datasource.server = The database server
 * kino.datasource.db = The database name
 * kino.datasource.timeout = Timeout for the database connection in seconds
 *
 * These must be used in the class that implements the interface!
 * */

public interface Database {
    int execute (PreparedStatement stmt) throws SQLException;
    ResultSet query (PreparedStatement stmt) throws SQLException;
    Connection getConnection() throws SQLException;
    public void CloseConnection() throws SQLException;
}
