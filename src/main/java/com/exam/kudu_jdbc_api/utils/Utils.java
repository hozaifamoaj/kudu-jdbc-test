package com.exam.kudu_jdbc_api.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class Utils {


    public static Long getCurrentTimeInMillis(){
        return System.currentTimeMillis() / 1000L;
    }

    public static Date getCurrentDate(){
        return new java.util.Date();
    }
}
