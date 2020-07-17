package com.grigor;

public class Iron implements Material {

    private final String name;

    public Iron(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Iron";
    }
}
