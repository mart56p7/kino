package kano.kino.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Film implements ModelInterface {
    private int id = 0;
    @NotNull
    @Size(min=2, max=50)
    private String name = null;
    @NotNull
    @Size(min=8, max=250)
    @Override
    public int getId() {
        return 0;
    }
    public Film(){}

    public Film(int id, @NotNull @Size(min = 2, max = 50) String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

