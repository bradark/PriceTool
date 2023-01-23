/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricingtool;

/**
 *
 * @author bradl
 */
public class Item {
    
    public Item(Double p, String n, String d){
        this.price = p;
        this.name = n;
        this.date = d;
    }
    
    public Double getPrice(){
        return price;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDate(){
        return date;
    }
    
    public void setPrice(Double p){
        this.price = p;
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    public void setDate(String d){
        this.date = d;
    }
    
    private Double price;
    private String name;
    private String date;
    
}
