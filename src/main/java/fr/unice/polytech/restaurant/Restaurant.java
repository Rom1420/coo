package fr.unice.polytech.restaurant;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.user.RegisteredUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String name;
    private List<Article> articlesSimples; //ensemble des artcile du restaurant
    private List<Menu> menusOfRestaurant; //ensemble des menus du restaurant
    private boolean isOpen;

    public Restaurant(String name, List<Article> articlesSimples, List<Menu> menusOfRestaurant) {
        this.name = name;
        this.articlesSimples = articlesSimples;
        this.menusOfRestaurant = menusOfRestaurant;
        this.isOpen=true; //arbitraire à voir comment on gère les ouvertures et fermetures du restaurant
    }

    public Restaurant(String name) {
        this.name = name;
        this.isOpen=true;
    }

    public void setOpen(boolean open){
        isOpen = open;
    }

    public List<Article> getArticlesSimples() {
        return articlesSimples;
    }

    public List<Menu> getMenusOfRestaurant() {
        return menusOfRestaurant;
    }

    public List<Article> selectAvailableArticle(){
        List<Article> availableArticle=new ArrayList<>();
        //TODO:faire l'algo quand on aura tout les éléments
        return availableArticle;
    }
    public List<Menu> calculateAvailbaleMenu(){
        List<Menu> availableMenu=new ArrayList<>();
        //TODO: de même pour cette méthode
        return availableMenu;
    }
    public void getDiscount(){ //TODO: à faire quand on aura la discountStrategy
        return;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean canPrepare(Order order, Map<RegisteredUser, Order> usersOrders) {
        return false;
    }

    @Override
    public String toString() {
        return "Restaurant{Nom :'" + name + "', Ouvert : '" + isOpen + "'}";
    }

    public String getName(){
        return this.name;
    }
}
