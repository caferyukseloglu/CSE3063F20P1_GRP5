package com.dls;
import java.util.ArrayList;

/**
 * The Assignment class stores assignments of an instance.
 * Instances may have more than one assignment which created by different users (bots).
 * Assignments should have at least one assigned label.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Assignment {

    private int instanceId;
    private Instance instance;
    private User user;
    private Datetime datetime;

    private ArrayList<Label> labels = new ArrayList<Label>();

    /*
     * Construct method of the Assignment class
     * @param   <Instance>          instance object to assign this object
     * @param   <Datetime>          datatime object to
     * @param   maxNumberOfLabels   maximum number of labels to assign a single instance
     * @return  nothing
     */
    public Assignment(Instance instance, Datetime datetime, User user) {

        // @todo datetime should be created under construct method, not as an input.
        setInstance(instance);
        setDatetime(datetime);
        setUser(user);

    }
    /*
     * Sets instance object as instance variable
     * Sets instance ID as intance variable
     * @todo check if it is suitable
     * @return  nothing
     */
    protected void setInstance(Instance instance) {
        this.instance = instance;
        this.instanceId = instance.getId();
    }
    /*
     * Sets datatime object as instance variable
     * @return  nothing
     */
    protected void setDatetime(Datetime datetime) {
        this.datetime = datetime;
    }
    /*
     * Sets user object as instance variable
     * @return  nothing
     */
    protected void setUser(User user) {
        this.user = user;
    }
    /*
     * Get id of instance object @todo check if it is suitable
     * @return  int             id of instance object
     */
    protected int getInstanceId(){
        return this.instanceId;
    }
    /*
     * Get user object
     * @return  <User>          user object
     */
    protected User getUser() {
        return this.user;
    }
    /*
     * Get datetime object
     * @return  <Datetime>      datetime object
     */
    protected Datetime getDatetime() {
        return this.datetime;
    }
    /*
     * Get array list of labels
     * @return  ArrayList<Label>    array list of labels
     */
    protected ArrayList<Label> getLabels() {
        return this.labels;
    }
    /*
     * Add assigned label to array list label.
     * @todo If maximum number of labels is reached, throw exception!
     * @param   <Label>             Label to assign.
     * @return  nothing
     */
    protected void addLabel(Label label) {
        if(this.labels.size() < this.instance.getDataset().getMaxNumberOfLabels()){
            this.labels.add(label);
        }
    }
    /*
     * Add label by label id. This method checks all label with given id
     * @return  nothing
     */
    protected void addLabelById(int id) {
        Label label = this.instance.getDataset().getLabelById(id);
        addLabel(label);
    }
    /*
     * Add label by label text. This method checks all label with given text
     * @return  nothing
     */
    protected void addLabelByText(String text) {
        Label label = this.instance.getDataset().getLabelByText(text);
        addLabel(label);
    }
    /*
     * Removes all labels that assigned.
     * @return  nothing
     */
    protected void removeAllLabels(){
        for(Label label : getLabels()){
            this.labels.remove(label);
        }
    }



}
