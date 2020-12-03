package com.groupfive;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class


public class Datetime {
    // this class converts datetime stamp to date time
    // @todo Functions are missing
    private String rawDatetime;

    public static void test(String[] args) {
        LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println("After formatting: " + formattedDate);
    }


    public Datetime(String rawDatetime){
        setRawDatetime(rawDatetime);
    }

    private void setRawDatetime(String rawDatetime){
        this.rawDatetime = rawDatetime;
    }

    protected String getRawDatetime(){
        return this.rawDatetime;
    }
}
