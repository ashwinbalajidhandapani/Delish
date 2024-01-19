package com.delish.Restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant){

    }
}
