package com.example.ambulanceapp.services;

import java.util.Random;

public class SecretKey {
    public static String makeSecretKey (){
        String key = "";
        int[] numSequence = {0,1,2,3,4,5,6,7,8,9};
        for(int i = 0; i < 6; i++){
            Random ran = new Random();
            key += (Integer.toString(ran.nextInt(numSequence.length)));
        }
        return key;
    }
}
