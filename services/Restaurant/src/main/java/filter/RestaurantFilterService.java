package filter;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.TypeCuisine;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantFilterService {
    public List<Restaurant> filterByCuisineType(List<Restaurant> restaurants, TypeCuisine cuisineType) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getTypeCuisine() == cuisineType)
                .collect(Collectors.toList());
    }

    public List<Restaurant> filterByOpeningTime(List<Restaurant> restaurants, DayOfWeek day, LocalTime time) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.isOpen(day, time))
                .collect(Collectors.toList());
    }

    public List<Restaurant> applyFilters(List<Restaurant> restaurants, TypeCuisine cuisineType, DayOfWeek day, LocalTime time) {
        if(cuisineType == null){
            return filterByOpeningTime(restaurants, day, time);
        }
        List<Restaurant> filteredByCuisine = filterByCuisineType(restaurants, cuisineType);
        return filterByOpeningTime(filteredByCuisine, day, time);
    }
}