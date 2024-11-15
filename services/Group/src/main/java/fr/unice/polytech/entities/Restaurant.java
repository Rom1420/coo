package fr.unice.polytech.entities;

import fr.unice.polytech.entities.DiscountType;

public class Restaurant {
    private String name;
    private DiscountType discountType; // Type de discount choisi pour ce restaurant
    private boolean isOpen;


    public Restaurant(String name, DiscountType discountType) {
        this.name = name;
        this.discountType = discountType;
    }

    public String getName(){
        return this.name;
    }

    public DiscountType getDiscountType() { return discountType; }

    public void setDiscountType(DiscountType discountType) { this.discountType=discountType; }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open){
        isOpen = open;
    }
}

