package kano.kino.service;

import kano.kino.model.Movie;
import kano.kino.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService implements CRUDServiceInterface<Movie> {

    private MovieRepository mr;

    @Autowired
    public MovieService(MovieRepository mr)  {
        this.mr = mr;
    }

    @Override
    public Movie create(Movie movie) throws SQLException {
        ResultSet rs = mr.createMovie(movie.getName(), movie.getGenre(), movie.getLength());
        movie = null;
        if (rs.next()) {
            movie = new Movie(movie.getId() ,movie.getName(), movie.getGenre(), movie.getLength());
        }
        return movie;
    }

    @Override
    public void edit(Movie movie) throws SQLException {
        mr.editMovie(movie.getId(), movie.getName(),  movie.getGenre(), movie.getLength());

    }

    @Override
    public void delete(int id) throws SQLException {
        mr.deleteMovie(id);
    }

    @Override
    public Movie getId(int id) throws SQLException {
        ResultSet rs = mr.getMovie(id);
        Movie movie = null;
        if (rs.next()) {
            movie = new Movie(rs.getInt("id"), rs.getString("name"), rs.getString("genre"), rs.getInt("length"));
        }
        return movie;
    }

    @Override
    public List<Movie> getAll() throws SQLException {
        ResultSet rs = mr.getAllMovies();
        List<Movie> users = new ArrayList<>();

        while (rs.next()) {
            users.add(new Movie(rs.getInt("id"), rs.getString("name"), rs.getString("genre"), rs.getInt("length")));
        }
        return users;
    }
}
