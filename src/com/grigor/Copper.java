package com.grigor;

public class Copper implements Material {

    private final String name;

    public Copper(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Copper";
    }
}
