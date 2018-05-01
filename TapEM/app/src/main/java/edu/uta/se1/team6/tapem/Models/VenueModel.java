package edu.uta.se1.team6.tapem.Models;

import java.io.Serializable;

/**
 * Created by yashodhan on 3/25/18.
 */

public class VenueModel implements Serializable {
    private String name;
    private int capacity;
    private int costPerCapacity = 2;


    public VenueModel(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.costPerCapacity = 2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCostPerCapacity() {
        return costPerCapacity;
    }

    public void setCostPerCapacity(int costPerCapacity) {
        this.costPerCapacity = costPerCapacity;
    }
}
