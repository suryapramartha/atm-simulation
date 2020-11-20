package com.mitrais.atm.util;

import java.util.Random;

public class RandomNumberGenerator {
    public RandomNumberGenerator(){}

    public  String getRandom6DigitNumber() {
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            return String.format("%06d", number);
    }
}
