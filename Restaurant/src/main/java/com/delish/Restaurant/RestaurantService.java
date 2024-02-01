package com.delish.Restaurant;


import com.delish.Restaurant.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.NoResultException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;
    private final Utility utility;
    public RestaurantService(RestaurantRepository restaurantRepository, JdbcTemplate jdbcTemplate, RestTemplate restTemplate, Utility utility){
        this.restaurantRepository = restaurantRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = restTemplate;
        this.utility = utility;
    }

    public List<Restaurant> getRestaurantList(){
        return restaurantRepository.findAll();
    }

//    public void registerRestaurant(Restaurant restaurant) {
//        restaurantRepository.saveAndFlush(restaurant);
//    }

    public void registerRestaurant(Restaurant restaurant){
        Long generatedId = utility.randomIdGenerator();
        List<Restaurant> res_id = jdbcTemplate.query("SELECT id FROM restaurant WHERE id = ? LIMIT 1",
                new IdRowMapper(),
                generatedId);
        if(res_id.contains(generatedId)){
            generatedId = utility.randomIdGenerator();
        }
        Restaurant restaurant_created = Restaurant.builder()
                .id(generatedId)
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .isopen(false)
                .build();
        restaurantRepository.saveAndFlush(restaurant_created);
    }

    public Boolean getRestaurantStatus(String restaurantName){
        List<Restaurant> isOpen = jdbcTemplate.query(
                "SELECT isopen FROM restaurant WHERE name =? LIMIT 1",
                new StatusRowMapper(),
                restaurantName);
        Optional<Restaurant> restaurantQueryResult = isOpen.stream().findFirst();

        if(restaurantQueryResult.get().getIsopen() == false) return false;
        return true;
    }

    public List<Restaurant> getRestaurantThatIsOpen(){
        List<Restaurant> openRestaurants = jdbcTemplate.query(
                "SELECT name, address, isopen, menuid FROM restaurant WHERE isopen=?",
                new OpenRestaurantsRowMapper(),
                true
        );
        return openRestaurants.stream().toList();

    }

    public Optional<Restaurant> getRestaurantInfo(String restaurantName){
        List<Restaurant> currentRestaurantInfo = jdbcTemplate.query(
                "SELECT * FROM restaurant WHERE name=? LIMIT 1",
                new CurrentRestaurantsRowMapper(),
                restaurantName
        );
        Optional<Restaurant> currentRestaurantQRes = currentRestaurantInfo.stream().findFirst();
        if(currentRestaurantQRes.isEmpty()) {
            log.warn("Restaurant not found");
        }
        return currentRestaurantQRes;
    }



    public static class StatusRowMapper implements RowMapper<Restaurant> {

        @Override
        public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Restaurant.builder()
                    .isopen(rs.getBoolean("isopen"))
                    .build();
        }
    }

    public static class OpenRestaurantsRowMapper implements RowMapper<Restaurant>{

        @Override
        public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Restaurant.builder()
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .isopen(rs.getBoolean("isopen"))
                    .menuid(rs.getInt("menuid"))
                    .build();
        }
    }

    public static class CurrentRestaurantsRowMapper implements RowMapper<Restaurant>{

        @Override
        public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Restaurant.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .isopen(rs.getBoolean("isopen"))
                    .menuid(rs.getInt("menuid"))
                    .build();
        }
    }

    public static class IdRowMapper implements RowMapper<Restaurant>{

        @Override
        public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Restaurant.builder().id(rs.getLong("id")).build();
        }
    }
}
