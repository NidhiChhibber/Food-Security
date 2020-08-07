package com.example.research_app.ui;

public class DBItemsClass {

    private String itemName, itemAddDate, itemAddTime;
    private int itemQuantity;
    public DBItemsClass ()
    {
    }
    public DBItemsClass(String name, int quantity, String date, String time)

    {
        this.itemName=name;
        this.itemQuantity = quantity;
        this.itemAddDate=date;
        this.itemAddTime=time;
    }

    public void setItemName(String name)
    {
        this.itemName = name;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemQuantity(int quantity) {
        this.itemQuantity = quantity;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemAddDate(String date) {
        this.itemAddDate = date;
    }

    public String getItemAddDate() {
        return itemAddDate;
    }

    public void setItemAddTime(String time) {
        this.itemAddTime = time;
    }

    public String getItemAddTime() {
        return itemAddTime;
    }
}
