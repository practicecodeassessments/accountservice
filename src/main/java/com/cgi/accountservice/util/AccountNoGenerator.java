package com.cgi.accountservice.util;

import java.util.Random;

public class AccountNoGenerator {

    public static String generate(){
        return String.format("%09d", new Random().nextInt(1000000000));
    }
}
