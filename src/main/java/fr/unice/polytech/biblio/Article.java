package fr.unice.polytech.biblio;

public class Article {
    private String name;
    private float price;
    private int timeRequiredForPreparation; //on part du principe que c'est en minute
    private String description;

    public Article(String name,float price,int timeRequiredForPreparation){
        this.name=name;
        this.price=price;
        this.timeRequiredForPreparation=timeRequiredForPreparation;
        this.description="none";
    }
    public Article(String name,float price,int timeRequiredForPreparation,String description){
        this.name=name;
        this.price=price;
        this.timeRequiredForPreparation=timeRequiredForPreparation;
        this.description=description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTimeRequiredForPreparation() {
        return timeRequiredForPreparation;
    }

    public void setTimeRequiredForPreparation(int timeRequiredForPreparation) {
        this.timeRequiredForPreparation = timeRequiredForPreparation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
