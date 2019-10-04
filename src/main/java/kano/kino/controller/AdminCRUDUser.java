package kano.kino.controller;

import kano.kino.model.User;
import kano.kino.service.LoggerService;
import kano.kino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user/")
public class AdminCRUDUser extends CRUDControllerAbstract <User, UserService> {

    @Autowired
    public AdminCRUDUser(@Qualifier("UserService") UserService userservice, @Qualifier("LoggerService") LoggerService loggerservice) {
        super("/admin/user", "user", "User", userservice, loggerservice);
    }
}
