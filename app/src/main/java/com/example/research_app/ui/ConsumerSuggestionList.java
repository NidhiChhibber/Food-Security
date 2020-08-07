package com.example.research_app.ui;

import java.util.ArrayList;

public class ConsumerSuggestionList
{
    private String id;
    private String name;
    private String shortDesc;
    private String country, state, city;
    private ArrayList<String> items;
    private int image;

    public void setId(String id) {
        this.id =id;
    }
    public void setName(String name) {
        this.name =name;
    }
    public void setShortdesc(String desc) {
        this.shortDesc = desc;
    }
    public void setCountry(String country){this.country = country;}
    public void setState(String state){this.state = state;}
    public void setCity(String city){this.city = city;}
    public void setItems(ArrayList<String> items) {this.items = items; }
    public void setImage(int img) {this.image = img; }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getShortdesc() {
        return shortDesc;
    }
    public String getCountry(){return this.country;}
    public String getState(){return this.state;}
    public String getCity(){return this.city;}
    public  ArrayList<String> getItems() {
        return items;
    }
    public int getImage() {
        return image;
    }
}

