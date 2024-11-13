package fr.unice.polytech.restaurant;

import fr.unice.polytech.discount.DiscountEngine;
import fr.unice.polytech.discount.DiscountType;
import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.user.RegisteredUser;

import javax.lang.model.element.TypeElement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.logging.Logger;

public class Restaurant {
    private String name;
    private List<Article> articlesSimples; //ensemble des artcile du restaurant
    private List<Menu> menusOfRestaurant; //ensemble des menus du restaurant

    private Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> weeklySchedules; // clé -> Jour de la semaine, valeur -> Plage horaire
    private int nbOfCook; //nombre de cuisinier afin de savoir combien peuvent etre produit d'item etc...
    private boolean isOpen;

    private TypeCuisine typeCuisine;
    private DiscountType discountType; // Type de discount choisi pour ce restaurant

    public Restaurant() {
        this.name = "";
        this.articlesSimples = new ArrayList<>();
        this.menusOfRestaurant = new ArrayList<>();
        this.weeklySchedules = new HashMap<>();
        this.isOpen = false;
        this.typeCuisine = TypeCuisine.AUTRE;
    }

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
        this.articlesSimples = new ArrayList<>();
        this.menusOfRestaurant = new ArrayList<>();
    }

    public Restaurant(String name,int nbOfCook){
        this.name=name;
        this.nbOfCook=nbOfCook;
        this.articlesSimples =new ArrayList<>();
        this.weeklySchedules = new HashMap<>();
        this.articlesSimples = new ArrayList<>();
        this.menusOfRestaurant = new ArrayList<>();
    }

    public Restaurant(String name, TypeCuisine typeCuisine, List<Article> articlesSimples, List<Menu> menusOfRestaurant, DiscountType discountType) {
        this.name = name;
        this.articlesSimples = articlesSimples;
        this.menusOfRestaurant = menusOfRestaurant;
        this.weeklySchedules = new HashMap<>();
        this.isOpen=false; //arbitraire à voir comment on gère les ouvertures et fermetures du restaurant
        this.typeCuisine = typeCuisine;
        this.discountType = discountType;
    }

    public void setOpen(boolean open){
        isOpen = open;
    }

    public void setName(String name) {
        this.name = name;
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

    public void addArticle(Article article) {articlesSimples.add(article);}
    public void addMenu(Menu menu) {menusOfRestaurant.add(menu);}

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
    public Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> getWeeklySchedules() {
        return this.weeklySchedules;
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

    public boolean isOpen(DayOfWeek date, LocalTime hour) {
        // NEW
        // Vérifie uniquement par heure, sans jour
        if (date == null && hour != null) {
            for (Map.Entry<DayOfWeek, Map.Entry<LocalTime, LocalTime>> entry : this.weeklySchedules.entrySet()) {
                LocalTime openingTime = entry.getValue().getKey();
                LocalTime closingTime = entry.getValue().getValue();

                if (!hour.isBefore(openingTime) && !hour.isAfter(closingTime)) {
                    this.isOpen = true;
                    return true;
                }
            }
            this.isOpen = false;
            return false;
        }

        // Vérifie uniquement par jour, sans heure
        if (hour == null && date != null) {
            this.isOpen = this.weeklySchedules.containsKey(date);
            return this.isOpen;
        }

        // Vérifie par jour et heure
        Map.Entry<LocalTime, LocalTime> schedules = this.weeklySchedules.get(date);
        if (schedules == null) {
            this.isOpen = false;
            return false;
        }

        boolean withinOpeningHours = !hour.isBefore(schedules.getKey()) && !hour.isAfter(schedules.getValue());
        this.isOpen = withinOpeningHours;
        return withinOpeningHours;
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

    public DiscountType getDiscountType() { return discountType; }

    public void setDiscountType(DiscountType discountType) { this.discountType=discountType; }

    public void setArticlesSimples(List<Article> articlesSimples) {
        this.articlesSimples = articlesSimples;
    }

    public void setMenusOfRestaurant(List<Menu> menusOfRestaurant) {
        this.menusOfRestaurant = menusOfRestaurant;
    }

    public void setWeeklySchedules(Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> weeklySchedules) {
        this.weeklySchedules = weeklySchedules;
    }
}
package fr.unice.polytech.restaurant;

        import java.time.DayOfWeek;
        import java.time.LocalDate;
        import java.time.LocalTime;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;

        import java.util.Map;
        import java.util.logging.Logger;
        import java.util.List;

public class RestaurantManager {
    private List<Restaurant> restaurants;
    private static final Logger logger = Logger.getLogger(RestaurantManager.class.getName());

    private RestaurantManager() {
        restaurants= new ArrayList<>();
    }

    // Singleton
    private static final RestaurantManager RESTAURANT_MANAGER_INSTANCE = new RestaurantManager();

    public static RestaurantManager getRestaurantManagerInstance() {
        return RESTAURANT_MANAGER_INSTANCE;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }
    public boolean deleteRestaurant(String name) {
        Restaurant restaurantToDelete = findRestaurantByName(name);
        if(restaurantToDelete != null){
            restaurants.remove(restaurantToDelete);
            return true;
        }
        return false;
    }


    public List<Restaurant> consultRestaurant(){
        return new ArrayList<>(restaurants);
    }

    public void clearRestaurants() {
        restaurants.clear();
    }

    public Restaurant findRestaurantByName(String name){
        for(Restaurant resto : restaurants){
            if(resto.getName().equals(name)){
                return resto;
            }
        }
        return null;
    }
    public List<Restaurant> searchOpenRestaurant(DayOfWeek date, LocalTime time){
        List<Restaurant> OpenRestaurants = new ArrayList<>();
        for(Restaurant resto : restaurants){
            if(resto.isOpen(date,time)){
                OpenRestaurants.add(resto);
            }
        }
        return OpenRestaurants;
    }

    public List<Restaurant> filterRestaurantsByCuisineType(TypeCuisine type, List<Restaurant> restaurants) {
        List<Restaurant> filteredRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.matchesCuisineType(type)) {
                filteredRestaurants.add(restaurant);
            }
        }
        return filteredRestaurants;
    }

    public void updateRestaurant(Restaurant updatedRestaurant, String oldRestaurantName) {
        Restaurant existingRestaurant = findRestaurantByName(oldRestaurantName);
        if (existingRestaurant != null) {
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setDiscountType(updatedRestaurant.getDiscountType());
            existingRestaurant.setOpen(updatedRestaurant.isOpen());
            existingRestaurant.setTypeCuisine(updatedRestaurant.getTypeCuisine());
            existingRestaurant.setNbOfCook(updatedRestaurant.getNbOfCook());
            existingRestaurant.setMenusOfRestaurant(updatedRestaurant.getMenusOfRestaurant());
            existingRestaurant.setWeeklySchedules(updatedRestaurant.getWeeklySchedules());

            logger.info("Restaurant updated successfully: " + updatedRestaurant.getName());
        } else {
            logger.warning("Restaurant with name " + oldRestaurantName + " not found.");
        }
    }

    public List<ScheduleDTO> convertScheduleToDTO(Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> weeklySchedules) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Map.Entry<DayOfWeek, Map.Entry<LocalTime, LocalTime>> entry : weeklySchedules.entrySet()) {
            String jour = entry.getKey().name();
            String horaireOuverture = entry.getValue().getKey().format(timeFormatter);
            String horaireFermeture = entry.getValue().getValue().format(timeFormatter);
            scheduleDTOs.add(new ScheduleDTO(jour, horaireOuverture, horaireFermeture));
        }

        return scheduleDTOs;
    }
}
package fr.unice.polytech.restaurant;

public class ScheduleDTO {
    private String jour;
    private String horaireOuverture;
    private String horaireFermeture;

    public ScheduleDTO(String jour, String horaireOuverture, String horaireFermeture) {
        this.jour = jour;
        this.horaireOuverture = horaireOuverture;
        this.horaireFermeture = horaireFermeture;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHoraireOuverture() {
        return horaireOuverture;
    }

    public void setHoraireOuverture(String horaireOuverture) {
        this.horaireOuverture = horaireOuverture;
    }

    public String getHoraireFermeture() {
        return horaireFermeture;
    }

    public void setHoraireFermeture(String horaireFermeture) {
        this.horaireFermeture = horaireFermeture;
    }
}

