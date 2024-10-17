package fr.unice.polytech.restaurant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    private List<Restaurant> restaurants;

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

    public List<Restaurant> consultRestaurant(){
        return new ArrayList<>(restaurants);
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
}
