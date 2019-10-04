package kano.kino.controller;

import kano.kino.model.User;
import kano.kino.model.UserType;
import kano.kino.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

/**
 * An abstract class, that will suite most Controllers that are part of the MORCMS.
 *
 * Gives access to the cms path.
 * Gives access to the userType
 * */
public abstract class ControllerAbstract {
    //Path to CMS incase we need to login
    protected String cmspath = "admin/";
    @Autowired
    LoggerService logger;

    @ModelAttribute("userType")
    public UserType userType(HttpSession session){
        /*
        Administrator (1, "Administrator"),
        Guest (2, "Guest"), //Same as Anonymous, but cookies are available
        Registretated (3, "Registretated"),
        Anonymous (3, "Anonymous"); //Cookies are not available for anonymous users
         */
        Object user = session.getAttribute("user");
        if(user instanceof User){
            return ((User)user).getUserType();
        }
        return UserType.ANONYMOUS;
    }
}
