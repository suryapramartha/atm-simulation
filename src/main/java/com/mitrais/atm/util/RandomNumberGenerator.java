package com.mitrais.atm.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomNumberGenerator {
    public RandomNumberGenerator(){}

    public  String getRandom6DigitNumber() {
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            return String.format("%06d", number);
    }
}
