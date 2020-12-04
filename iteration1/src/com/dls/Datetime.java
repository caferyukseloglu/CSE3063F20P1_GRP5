package com.dls;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

/**
 * The Datetime class stores raw (String) values and provides several methods to get and sort date/time.
 * @todo Create a method to get current timestamp
 * @todo Create a method to convert raw value to dynamic datetime
 * @todo Check other classes (especially RandomBot) for deprecated methods.
 * @todo Add comments
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Datetime {
    // this class converts datetime stamp to date time

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
