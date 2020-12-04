package com.dls;
import java.util.ArrayList;

/**
 * The Instance class stores variables and methods for each instance that created by WriteJSON class.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Instance {

    private int id;
    private String text;
    private boolean status = false; // @todo Is it needed to show it has assigned or not?
    private Dataset dataset;

    private ArrayList<Assignment> assignments = new ArrayList<Assignment>(); // Make it limited

    public Instance(int id, String text, Dataset dataset) {

        setId(id);
        setText(text);
        setDataset(dataset);

    }
    /*
     * Sets id of "instance" as instance variable
     * @return  nothing
     */
    private void setId(int id) {
        this.id = id;
        // @todo Check if it used before
    }
    /*
     * Sets text of "instance" as instance variable
     * @return  nothing
     */
    private void setText(String text) {
        this.text = text;
    }
    /*
     * Sets object of parent dataset as instance variable
     * @return  nothing
     */
    private void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
    /*
     * Gets id of instance
     * @return  int                 instance id
     */
    protected int getId() {
        return this.id;
    }
    /*
     * Gets id of instance
     * @return  String              instance text
     */
    protected String getText() {
        return this.text;
    }
    /*
     * Gets array list of instance object
     * @return  ArrayList<Assignment>   array list of assignment object
     */
    protected ArrayList<Assignment> getAssignments() {
        return this.assignments;
    }
    /*
     * Gets object of parent dataset
     * @return  <Dataset>               dataset object
     */
    protected Dataset getDataset() {
        return this.dataset;
    }
    /*
     * Updates text of instance
     * @return  nothing
     */
    protected void updateText(String text) {
        this.text = text;
    }
    /*
     * Creates new assignment objects and adds it to the array list of assignments.
     * @return  <Assignment>            assignment object
     */
    protected Assignment addAssignment(Datetime datetime, User user) {

        Assignment assignment = new Assignment(this, datetime, user);
        this.assignments.add(assignment);
        return assignment;
    }

}
