package kano.kino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class Admin {
    @GetMapping({"", "index"})
    public String get_root(){
        System.out.println("Index");
        return "/admin/index";
    }
}