package com.grigor;

public class Bolt implements Material {

    private final String name;

    public Bolt(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Bolt";
    }
}
