package kano.kino.controller;

import kano.kino.model.Movie;
import kano.kino.service.LoggerService;
import kano.kino.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/movies/")
public class AdminCRUDMovie extends CRUDControllerAbstract <Movie, MovieService> {
    @Autowired
    public AdminCRUDMovie(@Qualifier("movieService") MovieService movieservice, @Qualifier("LoggerService") LoggerService loggerservice) {
        super("admin/movies/", "movie", "movie", movieservice, loggerservice);
    }
}
