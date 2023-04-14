package model;

import model.enums.Resources;

import java.util.HashMap;

public class Government {

    private Colors color;
    private HashMap<Resources, Integer> resources;

    public HashMap<Resources, Integer> getResources() {
        return this.resources;
    }
}
