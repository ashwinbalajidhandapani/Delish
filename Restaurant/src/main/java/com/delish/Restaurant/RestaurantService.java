package com.delish.Restaurant;

import com.delish.Menu.ItemService;
import com.delish.Menu.Menu;
import com.delish.Menu.MenuRepository;
import com.delish.Menu.MenuService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;
    public RestaurantService(RestaurantRepository restaurantRepository, JdbcTemplate jdbcTemplate, RestTemplate restTemplate){
        this.restaurantRepository = restaurantRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = restTemplate;
    }

    public List<Restaurant> getRestaurantList(){
        return restaurantRepository.findAll();
    }

    public void registerRestaurant(Restaurant restaurant) {
        restaurantRepository.saveAndFlush(restaurant);
    }

    public void establishMenu(@RequestParam String restaurantname){
        for(Restaurant restaurant : getRestaurantList()){
            if(restaurant.getName().equals(restaurantname)){
                // Create a Menu

            }
        }
    }

}
