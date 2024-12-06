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
    private int groupId;
    private int userId;

    @JsonCreator
    public OrderImpl(
            @JsonProperty("menus") List<Menu> menus,
            @JsonProperty("articles") List<Article> articles,
            @JsonProperty("groupId") int groupId,
            @JsonProperty("userId") int userId){
        this.menus = menus != null ? menus : new ArrayList<>();
        this.articles = articles != null ? articles : new ArrayList<>();
        this.groupId = groupId;
        this.userId = userId;
    }

    public OrderImpl() {
        this.menus = new ArrayList<>();
        this.articles = new ArrayList<>();
        this.groupId = -1;
        this.userId = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
