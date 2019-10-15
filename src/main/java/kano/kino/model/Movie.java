package kano.kino.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Movie implements ModelInterface {
    private int id = 0;
    @NotNull
    @Size(min=2, max=50)
    private String name = null;
    @Size(min=0, max=50)
    private String genre = null;
    @NotNull
    @Size(min=20, max=240)
    private int length = 0;
    @Override
    public int getId() {
        return 0;
    }
    public Movie(){}

    public Movie(int id, @NotNull @Size(min = 2, max = 50) String name, @NotNull @Size(min = 0, max = 50) String genre, @NotNull @Size(min = 20, max = 240) int length) {
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

