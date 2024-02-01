package com.delish.Restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

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
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        }
        catch(Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restaurant);
        }

    }

    @GetMapping(path = "/all")
    public List<Restaurant> getRestaurantList(){
        return restaurantService.getRestaurantList();
    }

    @GetMapping(path = "/all/isOpen")
    public List<Restaurant> getOpenRestaurants(){
        return restaurantService.getRestaurantThatIsOpen();
    }
    @GetMapping(path = "/findStatus")
    public ResponseEntity<String> getRestaurantStatus(@RequestParam String restaurantName){
        try{
            if(restaurantService.getRestaurantStatus(restaurantName) == false) {
                return ResponseEntity.status(HttpStatus.OK).body("Restaurant is Closed");
            }
            else{
                return ResponseEntity.status(HttpStatus.OK).body("Restaurant is Open");
            }
        }
        catch (Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some issue with the endpoint");
        }
    }
    @GetMapping
    public ResponseEntity<Restaurant> getCurrentRestaurantInfo(@RequestParam String restaurantName){
        if(restaurantService.getRestaurantInfo(restaurantName).isEmpty()){
            log.warn("Restaurant Not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            Restaurant restaurant = restaurantService.getRestaurantInfo(restaurantName).get();
            return ResponseEntity.status(HttpStatus.OK).body(restaurant);
        }
    }

//    @PostMapping(path = "with_menu")
//    public ResponseEntity<Menu> createRestaurantWithMenu(@RequestBody Restaurant restaurant){
//
//    }
}
