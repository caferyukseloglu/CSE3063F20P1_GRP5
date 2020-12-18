package com.dls;
import java.util.ArrayList;

/**
 * The Instance class stores variables and methods for each instance that created by WriteJSON class.
 * @version iteration-1.0
 * @since 2020-12-01
 * @todo Is status variable needed to show instance has assigned or not?
 *
 */
public class Instance {

    private int id;
    private String text;
    private boolean status = false; //
    private Dataset dataset;

    private ArrayList<Assignment> assignments = new ArrayList<Assignment>(); // Make it limited
    /*
     * Construct method of the Instance class
     * @param   id                  unique id number of instance
     * @param   text                text of the dataset
     * @param   <Dataset>           parent dataset object
     * @return  nothing
     */
    public Instance(int id, String text, Dataset dataset) {

        setId(id);
        setText(text);
        setDataset(dataset);

    }
    /*
     * Sets id of "instance" as instance variable
     * @todo Check if it used before
     * @param   id                      instance id
     * @return                          nothing
     */
    private void setId(int id) {
        this.id = id;
    }
    /*
     * Sets text of "instance" as instance variable
     * @param   text                    instance text
     * @return                          nothing
     */
    private void setText(String text) {
        this.text = text;
    }
    /*
     * Sets object of parent dataset as instance variable
     * @param   dataset                 parent dataset of instance
     * @return                          nothing
     */
    private void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
    /*
     * Gets id of instance
     * @return                          instance id
     */
    protected int getId() {
        return this.id;
    }
    /*
     * Gets id of instance
     * @return                          instance text
     */
    protected String getText() {
        return this.text;
    }
    /*
     * Gets array list of instance object
     * @return                          array list of assignment object
     */
    protected ArrayList<Assignment> getAssignments() {
        return this.assignments;
    }
    /*
     * Gets object of parent dataset
     * @return                          parent dataset object
     */
    protected Dataset getDataset() {
        return this.dataset;
    }
    /*
     * Updates text of instance
     * @return                          nothing
     */
    protected void updateText(String text) {
        this.text = text;
    }
    /*
     * Creates new assignment objects and adds it to the array list of assignments.
     * @param   datetime                datetime object
     * @param   user                    user object
     * @return  <Assignment>            assignment object
     */
    protected Assignment addAssignment(Datetime datetime, User user) {

        Assignment assignment = new Assignment(this, datetime, user);
        this.assignments.add(assignment);
        return assignment;
    }

    protected boolean checkUserAssignment(User user){
        for(Assignment assignment : this.assignments){
            if(assignment.getUser().equals(user)){
                return true;
            }
        }
        return false;
    }

}
