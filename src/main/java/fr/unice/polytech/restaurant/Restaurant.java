package fr.unice.polytech.restaurant;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.user.RegisteredUser;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;

public class Restaurant {
    private String name;
    private List<Article> articlesSimples; //ensemble des artcile du restaurant
    private List<Menu> menusOfRestaurant; //ensemble des menus du restaurant
    private Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> weeklySchedules; // clé -> Jour de la semaine, valeur -> Plage horaire
    private int nbOfCook; //nombre de cuisinier afin de savoir combien peuvent etre produit d'item etc...
    private boolean isOpen;

    private TypeCuisine typeCuisine;


    public Restaurant(String name, TypeCuisine typeCuisine, List<Article> articlesSimples, List<Menu> menusOfRestaurant) {
        this.name = name;
        this.articlesSimples = articlesSimples;
        this.menusOfRestaurant = menusOfRestaurant;
        this.weeklySchedules = new HashMap<>();
        this.isOpen=false; //arbitraire à voir comment on gère les ouvertures et fermetures du restaurant
        this.typeCuisine = typeCuisine;
    }

    public Restaurant(String name, List<Article> articlesSimples, List<Menu> menusOfRestaurant) {
        this.name = name;
        this.articlesSimples = articlesSimples;
        this.menusOfRestaurant = menusOfRestaurant;
        this.weeklySchedules = new HashMap<>();
        this.isOpen=false; //arbitraire à voir comment on gère les ouvertures et fermetures du restaurant
        this.typeCuisine = TypeCuisine.AUTRE;
    }

    public Restaurant(String name) {
        this.name = name;
        this.isOpen=true;
        this.articlesSimples =new ArrayList<>();
        this.weeklySchedules = new HashMap<>();
    }

    public Restaurant(String name,int nbOfCook){
        this.name=name;
        this.nbOfCook=nbOfCook;
        this.articlesSimples =new ArrayList<>();
        this.weeklySchedules = new HashMap<>();
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

    public int getNbOfCook() {return nbOfCook;}
    public void setNbOfCook(int nbOfCook) {this.nbOfCook = nbOfCook;}
    public void setTypeCuisine(TypeCuisine typeCuisine) {
        this.typeCuisine = typeCuisine;
    }
    public void addArticle(Article article){
        if(this.articlesSimples == null){
            this.articlesSimples = new ArrayList<>();
        }
        this.articlesSimples.add(article);
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
    public void setSchedules(DayOfWeek day, LocalTime opening, LocalTime closing) {
        this.weeklySchedules.put(day, Map.entry(opening, closing));
    }
    public boolean isOpen(DayOfWeek date, LocalTime hour) {
        Map.Entry<LocalTime, LocalTime> schedules = weeklySchedules.get(date);

        if (schedules == null) {
            return false;
        }
        if(!hour.isBefore(schedules.getKey()) && !hour.isAfter(schedules.getValue())){
            this.isOpen=true;
            return true;
        }else {
            this.isOpen=false;
            return false;
        }
    }
    public Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> getWeeklySchedules() {
        return this.weeklySchedules;
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
    public TypeCuisine getTypeCuisine() {return typeCuisine;}

    // Filtrer par catégorie
    public List<Article> filterArticlesByCategory(Categorie categorie) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articlesSimples) {
            if (article.getCategorie() == categorie) {
                filteredArticles.add(article);
            }
        }
        return filteredArticles;
    }

    // Filtrer par prix
    public List<Article> filterArticlesByMaxPrice(float maxPrice) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articlesSimples) {
            if (article.getPrice() <= maxPrice) {
                filteredArticles.add(article);
            }
        }
        return filteredArticles;
    }

    // Filtrer par temps de préparation
    public List<Article> filterArticlesByMaxPreparationTime(int maxTime) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articlesSimples) {
            if (article.getTimeRequiredForPreparation() <= maxTime) {
                filteredArticles.add(article);
            }
        }
        return filteredArticles;
    }

    public boolean matchesCuisineType(TypeCuisine type) {
        return this.typeCuisine == type;
    }
    public int calculateNbOfArticleCanBePrepared(Article article,int timeInterval){
        int nbOfCooker = this.getNbOfCook();
        int preparationtime = article.getTimeRequiredForPreparation()*60; //pour le mettre en secondes
        if(preparationtime==0 || nbOfCooker==0){
            return 0;
        }
        int preparationTimeForOneArticle = preparationtime/nbOfCooker;
        if (preparationTimeForOneArticle==0){return 0;}
        int nbOfArticle = timeInterval/preparationTimeForOneArticle; //je travaille ici en seconde pour eviter les problèmes de division par 0
        return nbOfArticle;
    }
}

