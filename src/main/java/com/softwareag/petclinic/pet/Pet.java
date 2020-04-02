package com.softwareag.petclinic.pet;

public class Pet {
    private String id;
    private String name;
    private OwnerReference owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public OwnerReference getOwner() {
        return owner;
    }

    public void setOwner(OwnerReference owner) {
        this.owner = owner;
    }
}
