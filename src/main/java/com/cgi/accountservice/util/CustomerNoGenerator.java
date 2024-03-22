package com.cgi.accountservice.util;

import java.util.Random;

public class CustomerNoGenerator
{

    public static long generate(){
        return  new Random().nextInt(100000000);
    }
}
