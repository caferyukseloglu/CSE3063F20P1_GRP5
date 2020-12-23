package com.dls;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Assignment class stores assignments of an instance.
 * Instances may have more than one assignment which created by different users (bots).
 * Assignments should have at least one assigned label.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Assignment {

    private Datetime datetime;
    private Instance instance;
    private User user;
    private ArrayList<Label> labels = new ArrayList<Label>();

    /*
     * Construct method of the Assignment class
     * @param   instance                instance object to assign this object
     * @param   user                    user object to assign this object
     * @return  nothing
     */
    public Assignment(Instance instance, User user) {

        setInstance(instance);
        setDatetime(new Datetime());
        setUser(user);

    }



    /*
     * Sets datatime object as instance variable
     * @param   datetime                datetime object to set
     * @return                          nothing
     */
    protected void setDatetime(Datetime datetime) {
        this.datetime = datetime;
    }
    /*
     * Sets instance object as instance variable
     * @param   instance                instance object to set object
     * @return  nothing
     */
    protected void setInstance(Instance instance) {
        this.instance = instance;
    }
    /*
     * Sets user object as instance variable
     * @param   user                    user object to set
     * @return                          nothing
     */
    protected void setUser(User user) {
        this.user = user;
    }



    /*
     * Get datetime object
     * @return                          datetime object
     */
    protected Datetime getDatetime() {
        return this.datetime;
    }
    /*
     * Get instance object
     * @return                          instance object
     */
    protected Instance getInstance(){
        return this.instance;
    }
    /*
     * Get user object
     * @return                          user object
     */
    protected User getUser() {
        return this.user;
    }
    /*
     * Get array list of labels
     * @return                          array list of labels
     */
    protected ArrayList<Label> getLabels() {
        return this.labels;
    }



    /*
     * Get list of label IDs to compare equalities.
     * v2
     * @return                          array list of label IDs
     */
    protected List<Integer> getLabelIDs(){
        List<Integer> labelWithIDs = new ArrayList<Integer>();
        for(Label label : this.labels){
            labelWithIDs.add(label.getId());
        }
        return labelWithIDs;
    }
    /*
     * Add assigned label to array list label.
     * @todo If maximum number of labels is reached, throw exception!
     * @param   label                   label to assign.
     * @return                          nothing
     */
    protected void addLabel(Label label) {
        if(this.labels.size() < this.instance.getDataset().getMaxNumberOfLabels()){
            this.labels.add(label);
            this.instance.getTheMostFrequentLabel();
        }else{
            // @todo CLI display error.
            System.out.println(label.getText()+" could not assigned, due to exceed maximum number of labels.");
        }
    }
    /*
     * Add label by label id. This method checks all label with given id
     * @param   id                      id of label to add labels list
     * @return                          nothing
     */
    protected void addLabelById(int id) {
        Label label = this.instance.getDataset().getLabelById(id);
        addLabel(label);
    }
    /*
     * Add label by label text. This method checks all label with given text
     * @param   text                    text of label to add labels list
     * @return                          nothing
     */
    protected void addLabelByText(String text) {
        Label label = this.instance.getDataset().getLabelByText(text);
        addLabel(label);
    }
    /*
     * Removes all labels that assigned.
     * @return                          nothing
     */
    protected void removeAllLabels(){
        for(Label label : getLabels()){
            this.labels.remove(label);
        }
    }
    /*
     * Compares label lists of this assignment and given assignment.
     * @param   <Assignment>            assignment object to compare
     * @return                          Boolean
     */
    protected Boolean compareAssignmentLabels(Assignment assignment){
        List<Integer> assignmentA = this.getLabelIDs();
        List<Integer> assignmentB = assignment.getLabelIDs();
        Collections.sort(assignmentA);
        Collections.sort(assignmentB);
        return assignmentA.equals(assignmentB);
    }
}
