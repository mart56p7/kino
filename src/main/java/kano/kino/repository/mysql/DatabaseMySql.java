package kano.kino.repository.mysql;

import kano.kino.repository.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

/**
 * MySql implementation of the FileRepository Database
 *
 * Gives access to use
 * - DML through the method query
 * - DDL through the method execute
 * Open and Close a SQL connection
 * */

@Repository("DatabaseMySql")
public class DatabaseMySql implements Database {

    private Connection Conn = null;

    @Value("${kino.datasource.port}")
    private int port;
    @Value("${kino.datasource.username}")
    private String username;
    @Value("${kino.datasource.password}")
    private String password;
    @Value("${kino.datasource.server}")
    private String server;
    @Value("${kino.datasource.db}")
    private String db;
    @Value("${kino.datasource.timeout}")
    int timeout;

    @Autowired
    public DatabaseMySql(){

    }

    private Connection newConnection() throws SQLException {
        this.Conn = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + db, username, password);
        return Conn;
    }

    /**
     * Returns an active connection. If the current connection isnt active a new connection is automaticly created and returned.
     * */
    public Connection getConnection() throws SQLException {
        Connection c;
        try {
            if(Conn == null){
                c =  newConnection();
            }
            else if(Conn.isValid(this.timeout)){
                c = Conn;
            }
            else{
                c =  newConnection();
            }
        } catch (SQLException e) {
            c=  newConnection();
        }
        return c;
    }

    /**
     * Close the current connection
     * */
    public void CloseConnection() throws SQLException {
        if(Conn != null) {
            Conn.close();
        }
    }

    /**
     * Used for executing DDL commands against the SQL server
     * */
    public int execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeUpdate();
    }

    /**
     * Used for executing DML commands against the SQL server
     * */
    public ResultSet query(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }
}
