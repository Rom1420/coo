package fr.unice.polytech.restaurant;

import java.util.List;

public class Menu {
    public String name;
    public float price;
    public List<Article> articlesInMenu;

    public Menu(String name, float price) {
        this.name = name;
        this.price = price;
    }
    public void addArticleInMenu(Article article){
        this.articlesInMenu.add(article);
    }
}
