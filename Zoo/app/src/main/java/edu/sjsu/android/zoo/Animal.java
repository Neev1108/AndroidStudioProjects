package edu.sjsu.android.zoo;

public class Animal {

    String name;
    int image;
    String description;

    public Animal(String name, int image, String description){
        this.name = name;
        this.image = image;
        this.description = description;
    }
    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

}
