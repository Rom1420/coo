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