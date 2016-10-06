package com;

/**
 * Created by User on 28.08.2016.
 */
public class EmplModel {
    private int id;
    private String name;

    public EmplModel() {
    }

    public EmplModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "EmplModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
