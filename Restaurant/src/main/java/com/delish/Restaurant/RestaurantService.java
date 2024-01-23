package com.delish.Restaurant;

import com.delish.Menu.ItemService;
import com.delish.Menu.Menu;
import com.delish.Menu.MenuRepository;
import com.delish.Menu.MenuService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final JdbcTemplate jdbcTemplate;
    private final MenuService menuService;
    public RestaurantService(RestaurantRepository restaurantRepository, JdbcTemplate jdbcTemplate, MenuService menuService){
        this.restaurantRepository = restaurantRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.menuService = menuService;
    }

    public void registerRestaurant(Restaurant restaurant) {
        restaurantRepository.saveAndFlush(restaurant);
        try{
            Menu suppliedValue = new Menu();
            String menuId = menuService.createMenu(suppliedValue).toHexString();
            System.out.println(menuId);
            jdbcTemplate.update("UPDATE restaurant SET menuId = ? WHERE name=?", menuId, restaurant.getName());
        }
        catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }

}
