package com.example.ambulanceapp.services;

import java.util.Random;

public class SecretKey {
    private static Random ran = new Random();
    public static String makeSecretKey (){
        String key = "";
        int[] numSequence = {0,1,2,3,4,5,6,7,8,9};
        for(int i = 0; i < 6; i++){
            key += (Integer.toString(numSequence[ran.nextInt(numSequence.length)]));
        }
        return key;
    }

    public static String makeUniqueKey (){
        String uniquekey = "";
        String[] values = {"a","B","c","D","e"};
        for(int i = 0; i < 4; i++){
            uniquekey += values[ran.nextInt(values.length)];
        }
        return uniquekey.concat(makeSecretKey());
    }
}
