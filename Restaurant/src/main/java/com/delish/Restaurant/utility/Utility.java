package com.delish.Restaurant.utility;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utility {
    public Long randomIdGenerator(){
        Random random = new Random();
        return random.nextLong();
    }
}
