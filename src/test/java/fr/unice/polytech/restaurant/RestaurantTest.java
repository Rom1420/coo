package fr.unice.polytech.restaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    private Restaurant restaurant;
    private Article entree, plat, dessert;

    @BeforeEach
    void setUp() {
        entree = new Article("Salade", 5.5f, 10, Categorie.ENTREE);
        plat = new Article("Steak", 15.0f, 20, Categorie.PLAT);
        dessert = new Article("Glace", 4.0f, 5, Categorie.DESSERT);

        List<Article> articles = new ArrayList<>();
        articles.add(entree);
        articles.add(plat);
        articles.add(dessert);

        restaurant = new Restaurant("Le Gourmet", articles, new ArrayList<>());
    }

    @Test
    void testFilterByCategory() {
        // Filtrer par catégorie "PLAT"
        List<Article> filteredArticles = restaurant.filterArticlesByCategory(Categorie.PLAT);
        assertEquals(1, filteredArticles.size());
        assertTrue(filteredArticles.contains(plat));
    }

    @Test
     void testFilterByMaxPrice() {
        // Filtrer par prix max de 10€
        List<Article> filteredArticles = restaurant.filterArticlesByMaxPrice(10.0f);
        assertEquals(2, filteredArticles.size());
        assertTrue(filteredArticles.contains(entree));
        assertTrue(filteredArticles.contains(dessert));
    }

    @Test
     void testFilterByMaxPreparationTime() {
        // Filtrer par temps de préparation max de 15 minutes
        List<Article> filteredArticles = restaurant.filterArticlesByMaxPreparationTime(15);
        assertEquals(2, filteredArticles.size());
        assertTrue(filteredArticles.contains(entree));
        assertTrue(filteredArticles.contains(dessert));
    }
}
