package fr.unice.polytech.interfaces;

import fr.unice.polytech.entities.Article;
import fr.unice.polytech.entities.Menu;

import java.util.List;

public interface OrderInterface {
    void addMenu(Menu menu);
    void addArticle(Article article);
    float getTotalPrice();
    int getTotalPreparationTime();
    List<Menu> getMenus();
    List<Article> getArticles();
}


