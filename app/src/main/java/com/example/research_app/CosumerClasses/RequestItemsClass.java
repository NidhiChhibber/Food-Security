package com.example.research_app.CosumerClasses;

public class RequestItemsClass
{
    private String itemName;
    String itemDate,itemUnits;
    int itemQuantity;

    public  RequestItemsClass() {}

    public void setName(String name ) {this.itemName = name;}
    public void setDate(String date ) { this.itemDate = date;}
    public void setQuantity( int quantity) {this.itemQuantity=quantity;}
    public void setUnits( String units) {this.itemUnits = units;}
    public  String getName( ) {return this.itemName;}
    public String getDate( ) {return this.itemDate;}
    public int getQuantity( ) {return this.itemQuantity;}
    public String getUnits( ) {return this.itemUnits;}



}
