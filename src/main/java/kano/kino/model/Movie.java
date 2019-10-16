package kano.kino.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.*;

public class Movie implements ModelInterface {
    private int id = 0;
    @NotNull
    @Size(min=2, max=50)
    private String name = null;
    @NotNull
    @Size(min=0, max=50)
    private String genre = null;

    @Min(20)
    @Max(240)
    private int length = 0;
    @Override
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Movie(){}

    public Movie(int id, @NotNull @Size(min = 2, max = 50) String name, @NotNull @Size(min = 0, max = 50) String genre, @NotNull @Min(20) @Max(240) int length) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.length = length;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

