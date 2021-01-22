package com.group724.icons;


public class Item {
    private String name, category, ID;
    private int available;

    Item()  {}
    public String getName() {return name;}
    public String getID() {return ID;}
    public void setID(String inp) {this.ID = inp;}
    public String getCategory() {return category;}
    public int getAvailable() {return available;}
};