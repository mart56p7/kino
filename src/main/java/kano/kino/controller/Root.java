package kano.kino.controller;

import kano.kino.model.User;
import kano.kino.model.UserType;
import kano.kino.service.AuthorizationService;
import kano.kino.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
@RequestMapping("/")
public class Root extends ControllerAbstract{
    @Autowired
    AuthorizationService as;

    @GetMapping({"", "index", "/"})
    public String get_root(){
        System.out.println("Index");
        return "index";
    }

    @GetMapping("login")
    public String get_login(HttpSession session){
        if(userType(session) != UserType.ADMINISTRATOR){
            logger.log("get_login > Failed login", userName(session), LoggerService.CONTROLLER_MSG);
            return "login";
        }
        return "index";
    }

    @PostMapping("login")
    public String post_login(User user, Model model, HttpSession session)  {
        System.out.println("Login attempt");
        try {
            //Returns a user object on success or null on failure
            session.setAttribute("user", as.validateUser(user));
            //If the user hasnt logged in, we want to validate the user
            if(!(userType(session) == UserType.ADMINISTRATOR || userType(session) == UserType.EMPLOYEE)){
                logger.log("post_login > Failed login", userName(session), LoggerService.CONTROLLER_MSG);
                return "login";
            }
            //If we dont specificly update the userType, it takes 1 more load of the page for it to update..
            model.addAttribute("userType", userType(session));
            logger.log("post_login > Failed login", userName(session), LoggerService.CONTROLLER_MSG);
            return "index";
        } catch (SQLException e) {
            logger.log("post_login", e, userName(session), LoggerService.CONTROLLER_MSG);
            return cmspath+"error";
        }
    }

    @GetMapping("logout")
    public String get_logout(HttpSession session){
        logger.log("logout", userName(session), LoggerService.CONTROLLER_MSG);
        session.invalidate();
        return "index";
    }

    @GetMapping("auditorium")
    public String get_auditorium(){
        return "auditorium";
    }
}
