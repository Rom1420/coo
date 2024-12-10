package fr.unice.polytech.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.entities.Article;
import fr.unice.polytech.entities.Menu;
import fr.unice.polytech.interfaces.OrderInterface;

import java.util.ArrayList;
import java.util.List;

public class OrderImpl implements OrderInterface {
    private int id;
    private List<Menu> menus;
    private List<Article> articles;

    @JsonCreator
    public OrderImpl(
            @JsonProperty("menus") List<Menu> menus,
            @JsonProperty("articles") List<Article> articles) {
        this.menus = menus != null ? menus : new ArrayList<>();
        this.articles = articles != null ? articles : new ArrayList<>();
    }

    public OrderImpl() {
        this.menus = new ArrayList<>();
        this.articles = new ArrayList<>();
    }

    // Getter et Setter pour `id`
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    @Override
    public void addArticle(Article article) {
        articles.add(article);
    }

    @JsonIgnore
    @Override
    public float getTotalPrice() {
        float totalPrice = 0;
        for (Menu menu : menus) {
            totalPrice += menu.getPrice();
        }
        for (Article article : articles) {
            totalPrice += article.getPrice();
        }
        return totalPrice;
    }

    @JsonIgnore
    @Override
    public int getTotalPreparationTime() {
        int totalTime = 0;
        for (Menu menu : menus) {
            totalTime += menu.getTotalTimeRequiredForPreparation();
        }
        for (Article article : articles) {
            totalTime += article.getTimeRequiredForPreparation();
        }
        return totalTime;
    }

    @Override
    public List<Menu> getMenus() {
        return menus;
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }
}
