package fr.unice.polytech.server.microservices;


import fr.unice.polytech.db.OrderManager;
import fr.unice.polytech.entities.Menu;
import fr.unice.polytech.entities.Article;
import fr.unice.polytech.components.OrderImpl;

public class CreateOrder {
    public static void createOrder(Menu menu, Article article) {
        OrderImpl order = new OrderImpl();
        if (menu != null) {
            order.addMenu(menu);
        }
        if (article != null) {
            order.addArticle(article);
        }
        OrderManager.getOrderManagerInstance().addOrder(order);
    }
}
