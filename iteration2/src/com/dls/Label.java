package com.dls;

/**
 * The Label class stores variables and methods for each label that created initially.
 * @version iteration-2.0
 * @since 2020-12-01
 *
 */
public class Label {

    private Integer id;
    private String text;

    /*
     * Construct method of the Label class
     * @param   id                      unique id number of label
     * @param   text                    name of the dataset
     * @return                          nothing
     */
    public Label(int id, String text){

        setId(id);
        setText(text);

    }



    /*
     * Sets the id of label
     * @param   id                      unique id of label
     * @return                          nothing
     */
    private void setId(int id){
        this.id = id;
    }
    /*
     * Sets the id of label
     * @param   text                    unique text of label
     * @return                          nothing
     */
    private void setText(String text){
        this.text = text;
    }
    /*
     * Gets the id of label
     * @return                          id of label
     */
    protected Integer getId(){
        return this.id;
    }
    /*
     * Gets the text of label
     * @return                          text of label
     */
    protected String getText(){
        return this.text;
    }
    /*
     * Updates the text of label
     * @param   text                    text value to update
     * @return                          nothing
     */
    protected void updateText(String text){
        this.text = text;
    }
}