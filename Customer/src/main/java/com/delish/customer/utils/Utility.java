package com.delish.customer.utils;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;

@Component
@Log
public class Utility {
    private static final String[] validDomains = new String[]{
            "gmail.com",
            "yahoo.com",
            "northeastern.edu"
    };
    public Long generateRandomId(){
        Random random = new Random();
        long val = random.nextLong();
        return Math.abs(val);
    }

    public boolean validateEmail(String emailId){
        String[] emailSeperator = new String[2];
        if(emailId.contains("@")){
            emailSeperator = emailId.split("@");
            String domain = emailSeperator[1];
            System.out.println(domain);
            for(String s : validDomains){
                if(domain.equals(s)) return true;
            }
        }
        return false;
    }
}
