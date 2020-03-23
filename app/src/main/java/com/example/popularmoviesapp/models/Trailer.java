package com.example.popularmoviesapp.models;

public class Trailer {

    String name;
    String type;

    public Trailer(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
