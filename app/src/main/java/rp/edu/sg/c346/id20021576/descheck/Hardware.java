package rp.edu.sg.c346.id20021576.descheck;

import java.io.Serializable;

public class Hardware implements Serializable {

    private int id;
    private String name;
    private String desc;
    private float price;
    private float stars;

    public Hardware(String name, String desc, float price, float stars) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.stars = stars;
    }

    public Hardware(int id, String name, String desc, float price, float stars) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public Hardware setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hardware setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Hardware setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Hardware setPrice(float price) {
        this.price = price;
        return this;
    }

    public float getStars() {
        return stars;
    }

    public Hardware setStars(float stars) {
        this.stars = stars;
        return this;
    }
}
