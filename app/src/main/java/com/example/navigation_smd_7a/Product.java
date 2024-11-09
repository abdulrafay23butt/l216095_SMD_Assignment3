package com.example.navigation_smd_7a;

public class Product {
    int id;
    String title;
    int price;
    String status;
    String date;
    public Product() {
    }

    public Product(int id, String title, String date, int price, String status) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.status = status;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
