package com.carsales.model;

public class Car {
    private int id;
    private String make;
    private String model;
    private int year;
    private double price;
    private String description;
    private String[] images;

    public Car() {}



    public int getId() {
        return id;
    }
    public void setId(int id) {

        this.id = id;
    }
    public String getMake() {

        return make;
    }
    public void setMake(String make) {

        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {

        this.model = model;
    }
    public int getYear() {

        return year;
    }
    public void setYear(int year) {

        this.year = year;
    }
    public double getPrice() {

        return price;
    }
    public void setPrice(double price) {

        this.price = price;
    }
    public String getDescription() {

        return description;
    }
    public void setDescription(String description) {

        this.description = description;
    }
    public String[] getImages() {

        return images;
    }
    public void setImages(String[] images) {

        this.images = images;
    }
}