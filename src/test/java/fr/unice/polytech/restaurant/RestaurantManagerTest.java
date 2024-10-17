package fr.unice.polytech.restaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantManagerTest {
    private RestaurantManager restaurantManager;
    Restaurant restaurant1;
    Restaurant restaurant2;
    Restaurant restaurant3;


    @BeforeEach
    void setUp() {
        restaurantManager = RestaurantManager.getRestaurantManagerInstance();
        restaurantManager.clearRestaurants();

        // Créer des restaurants de test
        restaurant1 = new Restaurant("Restaurant A");
        restaurant1.setTypeCuisine(TypeCuisine.ITALIENNE);
        restaurant1.setSchedules(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(22, 0));

        restaurant2 = new Restaurant("Restaurant B");
        restaurant2.setTypeCuisine(TypeCuisine.FRANCAISE);
        restaurant2.setSchedules(DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(18, 0));

        restaurant3 = new Restaurant("Restaurant C");
        restaurant3.setTypeCuisine(TypeCuisine.ITALIENNE);
        restaurant3.setSchedules(DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(15, 0));

        restaurantManager.addRestaurant(restaurant1);
        restaurantManager.addRestaurant(restaurant2);
        restaurantManager.addRestaurant(restaurant3);
    }

    @Test
    void testSearchOpenRestaurant() {
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(11, 0);
        List<Restaurant> openRestaurants = restaurantManager.searchOpenRestaurant(day, time);

        assertEquals(1, openRestaurants.size());
        assertTrue(openRestaurants.contains(restaurant1));
    }

    @Test
    void testFilterRestaurantsByCuisineType() {
        // Test de filtrage par type de cuisine
        TypeCuisine cuisineType = TypeCuisine.ITALIENNE;
        List<Restaurant> filteredRestaurants = restaurantManager.filterRestaurantsByCuisineType(cuisineType, restaurantManager.consultRestaurant());

        // Vérifier que les bons restaurants sont filtrés
        assertEquals(2, filteredRestaurants.size()); // Restaurant A et Restaurant C
        assertTrue(filteredRestaurants.contains(restaurant1)); // Restaurant A
        assertTrue(filteredRestaurants.contains(restaurant3)); // Restaurant C
    }

    @Test
    void testSearchOpenAndFilterByCuisine() {
        // Test de la recherche de restaurants ouverts et de filtrage par type de cuisine
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(11, 0);
        TypeCuisine cuisineType = TypeCuisine.ITALIENNE;

        // Combiner les deux méthodes
        List<Restaurant> openRestaurants = restaurantManager.searchOpenRestaurant(day, time);
        List<Restaurant> filteredRestaurants = restaurantManager.filterRestaurantsByCuisineType(cuisineType, openRestaurants);

        // Vérifier que le résultat correspond aux attentes
        assertEquals(1, filteredRestaurants.size());
        assertTrue(filteredRestaurants.contains(restaurant1)); // Restaurant A
    }
}

