package com.delish.Restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant){
        try{
            restaurantService.registerRestaurant(restaurant);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(restaurant);
        }
        catch(Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restaurant);
        }

    }
}
