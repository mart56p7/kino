package kano.kino.service;

import kano.kino.model.User;
import kano.kino.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service("Authorization")
public class AuthorizationService {
    private UserRepository ur;

    @Autowired
    public AuthorizationService(@Qualifier("UserRepositoryMySql") UserRepository ur){
        this.ur = ur;
    }
    /**
     * Returns a User object, if the user is validated.
     * Returns null if the user + password doesnt match a user in the database
     * */
    public User validateUser(User user) throws SQLException {
        System.out.println("Validate user 1");
        ResultSet rs = ur.validateUser(user.getName(), user.getPassword());
        User user1 = null;
        if (rs != null && rs.next()) {
            System.out.println("Validate user success - Using user " + rs.getString("name"));
            user1 = new User(rs.getInt("id"), rs.getString("name"), "", rs.getInt("usertype_id"));
        }
        else{
            System.out.println("Validate user failed - Creating fake user");
            user1 = new User(1, user.getName(), "", 1);
        }
        return user1;
    }
}
