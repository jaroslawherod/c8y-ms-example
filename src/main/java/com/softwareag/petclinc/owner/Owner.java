package com.softwareag.petclinc.owner;

import java.util.List;

public class Owner  {
    private String id;
    private String name;
    private List<PetReference> pets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PetReference> getPets() {
        return pets;
    }

    public void setPets(List<PetReference> pets) {
        this.pets = pets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
