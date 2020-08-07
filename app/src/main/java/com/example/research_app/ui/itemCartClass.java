package com.example.research_app.ui;


public class itemCartClass {
    private RequiredItemsList itemObj;
    private int quantity;

    public itemCartClass(RequiredItemsList itemobj, int quantity) {
        this.itemObj = itemobj;
        this.quantity = quantity;
    }

    public RequiredItemsList getObj() {
        return itemObj;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity){this.quantity = newQuantity;}

}