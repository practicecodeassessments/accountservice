package com.cgi.accountservice.util;

import java.util.Random;

public class AccountNoGenerator {

    public static String generate(){
        return String.format("NL%09d", new Random().nextInt(1000000000));
    }
}
