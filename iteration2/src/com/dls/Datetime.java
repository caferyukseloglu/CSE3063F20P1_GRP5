package com.dls;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Datetime class stores raw (String) values and provides several methods to get and sort date/time.
 * @todo Create a method to get current timestamp (Next iteration)
 * @todo Create a method to convert raw value to dynamic datetime (Next iteration)
 * @todo Check other classes (especially RandomBot) for deprecated methods.
 * @todo Add comments
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Datetime {

    private LocalDateTime datetime;
    private DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
    private String datetimeText;

    /*
     * Construct method of the Datetime class
     * @return                          nothing
     */
    public Datetime(){

        setCurrentDatetime();
        setDatetimeText();

    }



    /*
     * Sets LocalDateTime object with current time.
     * @return                          nothing
     */
    private void setCurrentDatetime(){
        this.datetime = LocalDateTime.now();
    }
    /*
     * Sets LocalDateTime object as a text.
     * @return                          nothing
     */
    private void setDatetimeText(){
        datetimeText = this.datetime.format(this.datetimeFormatter);
    }
    /*
     * Gets LocalDateTime object as a text.
     * @return                          datetime text as defined format
     */
    protected String getDatetimeText(){
        return this.datetimeText;
    }
    /*
     * Gets LocalDateTime object as a text.
     * @return                          LocalDateTime object
     */
    protected LocalDateTime getDatetime(){
        return this.datetime;
    }



}
