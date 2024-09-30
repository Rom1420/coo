package fr.unice.polytech.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public String name;
    public float price;
    public int timeRequiredForPreparation;
    public List<Article> articlesInMenu;
    public Menu(String name, float price) {
        this.name = name;
        this.price = price;
        this.articlesInMenu = new ArrayList<>();
        this.timeRequiredForPreparation = this.calculateTotalTimeForPreparation();
    }

    public Menu(String name, float price, int time) {
        this.name = name;
        this.price = price;
        this.articlesInMenu = new ArrayList<>();
        this.timeRequiredForPreparation = time;
    }

    public float getPrice() {
        return price;
    }
    public void addArticleInMenu(Article article){
        this.articlesInMenu.add(article);
        price += article.getPrice();
        timeRequiredForPreparation += article.getTimeRequiredForPreparation();
    }

    public int getTotalTimeRequiredForPreparation() {
        return timeRequiredForPreparation;
    }

    public int calculateTotalTimeForPreparation(){
        int totalTime = 0;
        for(Article article : articlesInMenu){
            totalTime += article.getTimeRequiredForPreparation();
        }
        return totalTime;
    }

}
