package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private Order order;
    private Article article;
    private Menu menu;

    @BeforeEach
    void setUp() {
        Date orderDate = new Date();
        String deliveryLocation = "123 Street";
        order = new Order(orderDate, deliveryLocation);
        article = new Article("Burger", 10, 15); // Prix: 10, Temps de préparation: 15 minutes
        menu = new Menu("Menu1", 25, 30); // Prix: 25, Temps de préparation: 30 minutes
    }

    @Test
    void testAddArticle() {
        order.addArticle(article);
        assertEquals(1, order.getOrderedArticles().size());
        assertEquals(10, order.getTotalPrice());
        assertEquals(15, order.getTotalPreparationTime());
    }

    @Test
    void testAddMenu() {
        order.addMenu(menu);
        assertEquals(1, order.getOrderedMenus().size());
        assertEquals(25, order.getTotalPrice());
        assertEquals(30, order.getTotalPreparationTime());
    }

    @Test
    void testEstimatedDeliveryDate() {
        order.addArticle(article);
        order.addMenu(menu);
        long currentDate = System.currentTimeMillis();
        int preparationTIme = (article.getTimeRequiredForPreparation()+ menu.getTotalTimeRequiredForPreparation()) * 60000;
        assertEquals( currentDate + preparationTIme,  order.getEstimatedDeliveryDate().getTime());
    }

    @Test
    void testCannotAddArticleBeyondDeliveryTime() {
        Date deliveryDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10 minutes from now
        order = new Order(new Date(), deliveryDate, "123 Street");

        Exception exception = assertThrows(RuntimeException.class, () -> order.addArticle(article));
        assertEquals("Impossible d'ajouter ce menu, cela dépasserait la date de livraison.", exception.getMessage());
    }

    @Test
    void testCannotAddMenuBeyondDeliveryTime() {
        Date deliveryDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10 minutes from now
        order = new Order(new Date(), deliveryDate, "123 Street");

        Exception exception = assertThrows(RuntimeException.class, () -> order.addMenu(menu));
        assertEquals("Impossible d'ajouter ce menu, cela dépasserait la date de livraison.", exception.getMessage());
    }
    @Test
    void testGetOrderDate() {
        assertNotEquals(order.getOrderDate(), null);
    }
    @Test
    void testGetDeliveryDate() {
        assertNull(order.getDeliveryDate());
    }
    @Test
    void testGetDeliveryLocation() {
        assertEquals("123 Street", order.getDeliveryLocation());
    }
    @Test
    void testGetStatus() {
        assertEquals("en attente", order.getStatus());
    }
}
