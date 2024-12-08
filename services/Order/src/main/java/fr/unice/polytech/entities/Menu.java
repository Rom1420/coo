package fr.unice.polytech.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public String name;
    public float price;
    public int timeRequiredForPreparation;
    public List<Article> articlesInMenu;


    @JsonCreator
    public Menu(
            @JsonProperty("name") String name,
            @JsonProperty("price") float price,
            @JsonProperty("articlesInMenu") List<Article> articlesInMenu
    ) {
        this.name = name;
        this.price = price;
        this.articlesInMenu = articlesInMenu != null ? articlesInMenu : new ArrayList<>();
        this.timeRequiredForPreparation = calculateTotalTimeForPreparation();
    }
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

    public String getName() {
        return name;
    }

    public void addArticleInMenu(Article article){
        this.articlesInMenu.add(article);
        price += article.getPrice();
        timeRequiredForPreparation += article.getTimeRequiredForPreparation();
    }

    @JsonIgnore
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu Name: ").append(name).append("\n");
        sb.append("Price: ").append(price).append("\n");
        sb.append("Total Preparation Time: ").append(timeRequiredForPreparation).append(" minutes\n");
        sb.append("Articles in Menu:\n");
        for (Article article : articlesInMenu) {
            sb.append("- ").append(article.getName())
                    .append(": ").append(article.getPrice())
                    .append(" (").append(article.getTimeRequiredForPreparation()).append(" min)\n")
                    .append(" - ").append(article.getCategorie()).append("\n");
        }
        return sb.toString();
    }

    public List<Article> getArticlesInMenu() {
        return articlesInMenu;
    }

}
