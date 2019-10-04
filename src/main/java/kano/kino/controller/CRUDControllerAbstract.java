package kano.kino.controller;

import kano.kino.model.ModelInterface;
import kano.kino.model.UserType;
import kano.kino.service.LoggerService;
import kano.kino.service.CRUDServiceInterface;
import kano.kino.service.ModelFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Abstract implementation of a CRUD controller.
 *
 * This abstract implementation expect a POJO object to be created.
 * The POJO object must implement ModelInterface
 * The POJO can get simple datatypes set from the CRUDControllerAbstract.
 *
 * */

@Controller
public abstract class CRUDControllerAbstract
    <E extends ModelInterface,
     S extends CRUDServiceInterface<E>>
    extends ControllerAbstract {
    //Class attributes, set by constructor
    //Path for our Templates
    protected String path;
    //The modelname to use in Thymeleaf and which is used in our modelFactory
    protected String modelname;
    //Path to CMS incase we need to login
    protected String cmspath;
    //The name of the class, for logging of exceptions.
    protected String classname;
    //The service that the controller is making use of.
    protected S service;

    protected LoggerService logger;

    public CRUDControllerAbstract(String path, String classname, String modelname, S service, LoggerService logger){
        this(path, classname, modelname, service,"/", logger);
    }
    public CRUDControllerAbstract(String path, String classname, String modelname, S service, String cmspath, LoggerService logger){
        this.path = path;
        this.classname = classname;
        this.modelname = modelname;
        this.service = service;
        this.cmspath = cmspath;
        this.logger = logger;
        this.logger.setClassName(this.classname);
    }

    @GetMapping({"", "index"})
    public String get_root_index(Model model, HttpSession session)
    {
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath + "login";
        }
        try
        {
            model.addAttribute(modelname + "s", service.getAll());
            return path + "index";
        } catch (SQLException e) {
            logger.log("get_root_index", e, LoggerService.CONTROLLER_MSG);
            return cmspath + "error";
        }
    }

    @GetMapping("create")
    public String get_create(Model model, HttpSession session, ModelFactory modelfactory) {
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath + "login";
        }
        model.addAttribute(modelname, modelfactory.getModel(modelname));
        return path + "create";
    }

    @PostMapping("create")
    public String post_create(@ModelAttribute @Valid E e, BindingResult result, HttpSession session, Model model){
        model.addAttribute(modelname, e);
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath + "login";
        }
        if (result.hasErrors()) {
            return path + "create";
        }
        try {
            E newe = service.create(e);
            return "redirect:/"+path+"info/" + newe.getId();
        } catch (SQLException ex) {
            logger.log("post_create", ex, LoggerService.CONTROLLER_MSG);
            return "redirect:/"+cmspath;
        }
    }

    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model, HttpSession session)
    {
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath+"login";
        }
        try {
            model.addAttribute(modelname, service.getId(id));
            return path+"edit";
        } catch (SQLException e) {
            logger.log("get_edit", e, LoggerService.CONTROLLER_MSG);
            return cmspath + "error";
        }
    }

    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid E e, BindingResult result, HttpSession session, Model model)
    {
        model.addAttribute(modelname, e);
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath + "login";
        }
        if (result.hasErrors()) {
            return path + "edit";
        }
        try {
            service.edit(e);
            return "redirect:/" + path + "info/" + e.getId();
        } catch (SQLException ex) {
            logger.log("post_edit", ex, LoggerService.CONTROLLER_MSG);
            return "redirect:/" + cmspath + "error";
        }

    }

    @GetMapping("/delete/{id}")
    public String get_delete(@PathVariable int id, Model model, HttpSession session)
    {
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath + "login";
        }
        try {
            model.addAttribute(modelname, service.getId(id));
            return path + "delete";
        } catch (SQLException ex) {
            logger.log("get_delete", ex, LoggerService.CONTROLLER_MSG);
            return cmspath + "error";
        }
    }

    @PostMapping("/delete")
    public String post_delete(@RequestParam(value="id") int id, HttpSession session)
    {
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath + "login";
        }
        try {
            service.delete(id);
            return "redirect:/" + path;
        } catch (SQLException e) {
            logger.log("post_delete", e, LoggerService.CONTROLLER_MSG);
            return "redirect:/" + cmspath + "error";
        }
    }

    @GetMapping("/info/{id}")
    public String get_info(@PathVariable int id, Model model, HttpSession session)
    {
        if(userType(session) != UserType.ADMINISTRATOR)
        {
            return cmspath + "login";
        }
        try {
            model.addAttribute(modelname, service.getId(id));
            return path + "info";
        } catch (SQLException e) {
            logger.log("get_info", e, LoggerService.CONTROLLER_MSG);
            return "redirect:/" + cmspath + "error";
        }
    }
}
