package com.delish.Restaurant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
}
