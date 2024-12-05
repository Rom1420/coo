package fr.unice.polytech.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

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
        if (findRestaurantByName(restaurant.getName()) == null) {
            restaurants.add(restaurant);
        } else {
            logger.warning("Restaurant with name " + restaurant.getName() + " already exists.");
        }
    }

    public boolean deleteRestaurant(String name) {
        Restaurant restaurantToDelete = findRestaurantByName(name);
        if (restaurantToDelete != null) {
            restaurants.remove(restaurantToDelete);
            return true;
        }
        return false;
    }

    public List<Restaurant> consultRestaurant() {
        return new ArrayList<>(restaurants);
    }

    public void clearRestaurants() {
        restaurants.clear();
    }

    public Restaurant findRestaurantByName(String name) {
        for (Restaurant resto : restaurants) {
            if (resto.getName().equals(name)) {
                return resto;
            }
        }
        return null;
    }

    public List<Restaurant> searchOpenRestaurant(DayOfWeek date, LocalTime time) {
        List<Restaurant> openRestaurants = new ArrayList<>();
        for (Restaurant resto : restaurants) {
            if (resto.isOpen(date, time)) {
                openRestaurants.add(resto);
            }
        }
        return openRestaurants;
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
        Restaurant existingRestaurant = this.findRestaurantByName(oldRestaurantName);
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

}
