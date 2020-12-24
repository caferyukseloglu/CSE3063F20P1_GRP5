package com.dls;
import java.util.*;

/**
 * The Instance class stores variables and methods for each instance that created by WriteJSON class.
 * @version iteration-2.0
 * @since 2020-12-01
 *
 */
public class Instance {

    private Integer id;
    private String text;
    private Dataset dataset;
    private Label finalLabel;
    private ArrayList<Assignment> assignments = new ArrayList<Assignment>(); // Make it limited
    private ArrayList<User> users = new ArrayList<User>();

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
     * Gets users list
     * @return                          users list
     */
    protected ArrayList<User> getUsers(){
        return this.users;
    }
    /*
     * Gets total number of assignments
     * @return                          total number of assignments
     */
    protected int getNumberOfAssignments(){
        return this.assignments.size();
    }
    /*
     * Gets total number of unique assignments
     * @return                          total number of unique assignments
     */
    protected int getNumberOfUniqueAssignments(){
        List<List<Integer>> uniqueLists = new ArrayList<List<Integer>>();
        int i = 0;
        for(Assignment assignment : this.assignments){
            List<Integer> labelIDs = assignment.getLabelIDs();  // TO CHECK
            Collections.sort(labelIDs);
            boolean unique = true;
            for (List<Integer> uniqueList : uniqueLists){
                Collections.sort(uniqueList);
                if (uniqueList.equals(labelIDs)){
                    unique = false;
                    break;
                }
            }
            if (unique){
                uniqueLists.add(labelIDs);
                i++;
            }
        }
        return uniqueLists.size();
    }
    /*
     * Gets total number of users
     * @return                          total number of users
     */
    protected int getNumberOfUsers(){
        return this.users.size();
    }
    /*
     * Gets list of unique labels
     * @return                          list of unique labels
     */
    protected ArrayList<Label> getUniqueLabels(){
        ArrayList<Label> labels = new ArrayList<Label>();
        for(Assignment assignment : this.assignments){
            for(Label label : assignment.getLabels()){
                if(!labels.contains(label)){
                    labels.add(label);
                }
            }
        }
        return labels;
    }
    /*
     * Gets HashMap of Label and Integer distributions.
     * @return                          HashMap of Label and Integer distributions
     */
    protected HashMap<Label, Integer> getLabelFrequencies(){
        HashMap<Label, Integer> labelFrequencies = new HashMap<Label, Integer>();
        for(Assignment assignment : this.assignments){
            for(Label label : assignment.getLabels()){
                if(labelFrequencies.containsKey(label)){
                    labelFrequencies.replace(label, labelFrequencies.get(label)+1);
                }else{
                    labelFrequencies.put(label, 1);
                }
            }
        }
        return labelFrequencies;
    }
    /*
     * Gets HashMap of Label and Percentages distributions.
     * @return                          HashMap of Label and Percentage distributions
     */
    protected HashMap<Label, Double> getLabelPercentages(){
        HashMap<Label, Integer> labelFrequencies = getLabelFrequencies();
        HashMap<Label, Double> labelPercentages = new HashMap<Label, Double>();
        Integer totalNumberOfLabels = 0;
        for(Map.Entry<Label, Integer> entry : labelFrequencies.entrySet()){
            totalNumberOfLabels += entry.getValue();
        }
        for (Map.Entry<Label, Integer> entry : labelFrequencies.entrySet()){
            labelPercentages.put(entry.getKey(), entry.getValue().doubleValue()/totalNumberOfLabels.doubleValue()*100.0);
        }
        return labelPercentages;
    }
    /*
     * Gets HashMap of Final Label and Percentage distributions.
     * @return                          HashMap of Final Label and Percentage distributions
     */
    protected HashMap<Label, Double> getTheMostFrequentLabel(){
        HashMap<Label, Integer> labelFrequencies = this.getLabelFrequencies();
        HashMap<Label, Double> mostFrequent = new HashMap<Label, Double>();
        Map.Entry<Label, Integer> maxEntry = null;
        Integer totalNumberOfLabels = 0;
        for (Map.Entry<Label, Integer> entry : labelFrequencies.entrySet()){
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        for (Integer f : labelFrequencies.values()){
            totalNumberOfLabels += f;
        }
        mostFrequent.put(maxEntry.getKey(), maxEntry.getValue().doubleValue()*100.0);
        this.finalLabel = maxEntry.getKey();
        return mostFrequent;
    }
    /*
     * Gets Final Label of this object
     * @return                          Final label object
     */
    protected Label getFinalLabel(){
        return this.finalLabel;
    }
    /*
     * Gets entropy of label distribution.
     * @return                          entropy of label distribution
     */
    protected Double getLabelDistributionEntropy(){
        HashMap<Label, Double> labelPercentages = getLabelPercentages();
        Double totalEntropy = 0.0;
        for (Map.Entry<Label, Double> entry : labelPercentages.entrySet()){
            Double percentage = entry.getValue().doubleValue() / 100.0;
            totalEntropy += -1.0 * percentage * Math.log(percentage) / Math.log(2);
        }
        return totalEntropy;
    }
    /*
     * Gets list of assignments of given user
     * @param   user                    User object
     * @return                          List of assignments by user
     */
    protected ArrayList<Assignment> getAssignmentsOfUser(User user){
        ArrayList<Assignment> assignmentsOfUser = new ArrayList<Assignment>();
        for (Assignment assignment : this.assignments){
            if(assignment.getUser().equals(user)){
                assignmentsOfUser.add(assignment);
            }
        }
        return assignmentsOfUser;
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
    protected Assignment addAssignment(User user) {
        Assignment assignment = new Assignment(this, user);
        this.assignments.add(assignment);
        user.addDataset(this.dataset);
        user.addAssignment(assignment);
        this.addUser(user);
        this.dataset.addUser(user);
        return assignment;
    }
    /*
     * Adds user to users list if not exist.
     * @return                          nothing
     */
    protected void addUser(User user){
        if(!this.users.contains(user)){
            this.users.add(user);
        }
    }
    /*
     * Calculate assignment consistency of user. Compares two assignments.
     * @return                          assignment consistency
     */
    protected Boolean checkAssignmentConsistencyOfUser(User user){
        ArrayList<Assignment> assignmentsOfUser = getAssignmentsOfUser(user);
        for (int i = 0; i < assignmentsOfUser.size()-1; i++) {
            if(!assignmentsOfUser.get(i).compareAssignmentLabels(assignmentsOfUser.get(i+1))){
                return false;
            }
        }
        return true;
    }
    /*
     * Prints getLabelPercentages method.
     * @return                          nothing
     */
    protected void printLabelPercentages(){
        HashMap<Label, Double> labelPercentages = getLabelPercentages();
        for (Map.Entry<Label, Double> entry : labelPercentages.entrySet()){
            System.out.println(entry.getKey().getText() + " => " + entry.getValue() + "%");
        }
    }
    /*
     * Prints getTheMostFrequentLabel method.
     * @return                          nothing
     */
    protected void printMostFrequentLabel(){
        HashMap<Label, Double> mostFrequentLabel = getTheMostFrequentLabel();
        for (Map.Entry<Label, Double> entry : mostFrequentLabel.entrySet()){
            System.out.println(entry.getKey().getText() + " => " + entry.getValue() + "%");
        }
    }
    /*
     * Prints all performance metrics of instance.
     * @return                          nothing
     */
    protected void printPerformanceMetrics(){
        System.out.println("\u001B[34m" + "INSTANCE PERFORMANCE METRICS" + "\u001B[0m");
        System.out.println("1. Number of Assignments");
        System.out.println(getAssignments().size());
        System.out.println("2. Number of Unique Assignments");
        System.out.println(getNumberOfUniqueAssignments());
        System.out.println("3. Number of Unique Users");
        System.out.println(getNumberOfUsers());
        System.out.println("4. Most Frequent Label and Its Percentage");
        this.printMostFrequentLabel();
        System.out.println("5. List of Labels and Their Percentages");
        this.printLabelPercentages();
        System.out.println("6. Entropy of the Labels");
        System.out.println(this.getLabelDistributionEntropy());
    }

}
