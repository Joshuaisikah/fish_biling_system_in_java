/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.ica.part2;

public class YoFishItem {
    private int id;
    private String item;
    private double price;
    private int stock;
    private double maxSize;
    private int lowTemp;
    private int highTemp;

    public YoFishItem(int id, String item, double price,  int stock, double maxSize, int lowTemp, int highTemp){
        this.id = id;
        this.item = item;
        this.price = price;
        this.stock = stock;
        this.maxSize = maxSize;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getItem(){
        return this.item;
    } 
    
    public double getPrice(){
        return this.price;
    }
    
    public int getStock(){
        return this.stock;
    }

    public double getMaxSize(){
        return this.maxSize;
    }

    public int getLowTemp(){
        return this.lowTemp;
    }

    public int getHighTemp(){
        return this.highTemp;
    }
    
    public String getPicFilename(){
        return "./src/"+this.id+".jpg";
    }
}
