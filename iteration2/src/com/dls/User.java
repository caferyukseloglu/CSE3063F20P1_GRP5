package com.dls;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The User class stores variables and methods for each user that stated initially.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class User {

    private int id;
    private String name;
    private String type;
    private String password;
    private Datetime loginDatetime;
    private Datetime logoutDatetime;
    private Double consistencyCheckProbability;
    private ArrayList<Dataset> datasets = new ArrayList<Dataset>();
    private ArrayList<Assignment> assignments = new ArrayList<Assignment>();

    /*
     * Construct method of the User class
     * @param   id                      id of user
     * @param   name                    name of user
     * @param   type                    type of user
     * @param   password                password of user
     * @return                          nothing
     */
    public User(int id, String name, String type, String password){
        setId(id);
        setName(name);
        setType(type);
        setPassword(password);
    }
    /*
     * Sets the id of user
     * @todo Check if this id used before
     * @param   id                      unique id of user
     * @return                          nothing
     */
    private void setId(int id){
        this.id = id;

    }
    /*
     * Sets name of user
     * @param   text                    name of user
     * @return                          nothing
     */
    private void setName(String name){
        this.name = name;
    }
    /*
     * Sets type of user
     * @param   type                    type of user
     * @return                          nothing
     */
    private void setType(String type){
        this.type = type;
    }
    /*
     * Sets password of user
     * @param   password                password of user
     * @return                          nothing
     */
    private void setPassword(String password){
        this.password = password;
    }
    /*
     * Sets consistency check probability parameter of user
     * @param   consistency check probability   consistency check probability of user
     * @return                                  nothing
     */
    public void setConsistencyCheckProbability(Double consistencyCheckProbability){
        this.consistencyCheckProbability = consistencyCheckProbability;
    }
    /*
     * Gets id of user
     * @return                          user id
     */
    protected int getId(){
        return this.id;
    }
    /*
     * Gets type of user
     * @return                          user type
     */
    protected String getType(){
        return this.type;
    }
    /*
     * Gets name of user
     * @return                          user name
     */
    protected String getName(){
        return this.name;
    }
    /*
     * Gets password of user
     * @return                          user password
     */
    protected String getPassword(){
        return this.password;
    }
    /*
     * Gets consistency check probability of user
     * @return                          user consistency check probability
     */
    protected Double getConsistencyCheckProbability(){
        return  this.consistencyCheckProbability;
    }
    /*
     * Checks user name and password.
     * @return                          if credentials correct
     */
    protected Boolean checkPassword(String userName, String password){
        return this.getName().equals(userName) && this.getPassword().equals(password);
    }
    /*
     * Updates user name
     * @return                          nothing
     */
    protected void updateName(String name){
        this.name = name;
    }
    /*
     * Updates user type
     * @return                          nothing
     */
    protected void updateType(String type){
        this.type = type;
    }

    protected void addDataset(Dataset dataset){
        if(!this.datasets.contains(dataset)){
            this.datasets.add(dataset);
        }
    }

    protected void addAssignment(Assignment assignment){
        if(!this.assignments.contains(assignment)){
            this.assignments.add(assignment);
        }
    }

    protected Integer getTotalNumberOfInstancesLabeled(Dataset dataset, Boolean unique){
        Integer number = 0;
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<String> instanceTexts = new ArrayList<String>();
        for(Instance instance : dataset.getInstances()){
            if(instance.getUsers().contains(this)){
                if(unique) {
                    if(!instanceTexts.contains(instance.getText())){
                        instanceTexts.add(instance.getText());
                        number ++;
                    }
                }else{
                    instanceTexts.add(instance.getText());
                    number ++;
                }
            }
        }

        return number;
    }

    protected Integer getNumberOfDataset(){
        return this.datasets.size();
    }

    protected HashMap<Dataset, Double> getComplitionsOfAllDataset(){
        HashMap<Dataset, Double> complitionsOfAllDataset = new HashMap<Dataset, Double>();
        for(Dataset dataset : this.datasets){
            complitionsOfAllDataset.put(dataset, getComplitionOfDataset(dataset));
        }
        return complitionsOfAllDataset;
    }

    protected Double getComplitionOfDataset(Dataset dataset){
        Integer labeledInstanceNumberOfDataset = this.getTotalNumberOfInstancesLabeled(dataset, false);
        Integer instanceNumber = dataset.getNumberOfInstances();
        return (double) labeledInstanceNumberOfDataset/ instanceNumber;
    }

    protected void printComplitionsOfDataset(Dataset dataset){
        Double percentage = this.getComplitionOfDataset(dataset) * 100.0;
        System.out.println("Completeness Percentage of "+dataset.getName()+" : "+percentage.intValue()+" %");
    }

    protected void printComplitionsOfAllDataset(){
        for(Dataset dataset : this.datasets){
            this.printComplitionsOfDataset(dataset);
        }
    }


    protected void setLoginDatetime(){
        this.loginDatetime = new Datetime();
        this.logoutDatetime = this.loginDatetime;
    }

    protected void setLogoutDatetime(){
        this.logoutDatetime = new Datetime();
    }

    protected double getAverageTimeSpentInLabelingOld(Dataset dataset){
        int totalNumberOfInstancesLabeled = getTotalNumberOfInstancesLabeled(dataset, false);
        if(this.loginDatetime.equals(this.logoutDatetime)){
            Datetime secondDatetime = new Datetime();
            double totalSpent = java.time.Duration.between(this.loginDatetime.getDatetime(), secondDatetime.getDatetime()).getSeconds();
            return totalSpent/totalNumberOfInstancesLabeled;
        }else{
            double totalSpent = java.time.Duration.between(this.loginDatetime.getDatetime(), this.logoutDatetime.getDatetime()).getSeconds();
            return totalSpent/totalNumberOfInstancesLabeled;
        }
    }

    protected Double getAverageTimeSpentWhileLabeling(){
        Integer totalNumberOfInstancesLabeled = this.assignments.size();
        Double totalTimeSpent = 0.0;
        totalTimeSpent += java.time.Duration.between(this.loginDatetime.getDatetime(), this.assignments.get(0).getDatetime().getDatetime()).getSeconds();
        for(Integer i = 1; i < totalNumberOfInstancesLabeled ; i ++){
            totalTimeSpent += java.time.Duration.between(this.assignments.get(i-1).getDatetime().getDatetime(), this.assignments.get(i).getDatetime().getDatetime()).getSeconds();
        }
        return totalTimeSpent / totalNumberOfInstancesLabeled.doubleValue();
    }

    protected Double getStdDevOfTimeSpentWhileLabeling(){

        Integer totalNumberOfInstancesLabeled = this.assignments.size();
        ArrayList<Double> timesSpent = new ArrayList<Double>();
        Double totalTimeSpent = 0.0;
        totalTimeSpent += java.time.Duration.between(this.loginDatetime.getDatetime(), this.assignments.get(0).getDatetime().getDatetime()).getSeconds();
        for(Integer i = 1; i < totalNumberOfInstancesLabeled ; i ++){
            Double timeSpent = (double) java.time.Duration.between(this.assignments.get(i-1).getDatetime().getDatetime(), this.assignments.get(i).getDatetime().getDatetime()).getSeconds();
            totalTimeSpent += timeSpent;
            timesSpent.add(timeSpent);
        }


        Double standardDeviation = 0.0;


        Double mean = totalTimeSpent / timesSpent.size();

        for(Double time: timesSpent) {
            standardDeviation += Math.pow(time - mean, 2);
        }

        return Math.sqrt(standardDeviation/timesSpent.size());
    }

    protected Double getConsistencyPercentageOfAllDatasets(){

        Integer numberOfConsistentAssignments = 0;
        Integer numberOfTotalInstances = 0;
        for(Dataset dataset : this.datasets){
            for (Instance instance : this.getLabeledInstancesOfUser(dataset)){
                if(instance.checkAssignmentConsistencyOfUser(this)){
                    numberOfConsistentAssignments ++;
                }
                numberOfTotalInstances ++;
            }
        }
        if(numberOfTotalInstances == 0){
            // @todo may return null
            return 0.0;
        }else{
            return (double) numberOfConsistentAssignments/numberOfTotalInstances;
        }
    }

    protected Double getConsistencyPercentageOfDataset(Dataset dataset){

        Integer numberOfConsistentAssignments = 0;
        Integer numberOfTotalInstances = 0;

        for (Instance instance : this.getLabeledInstancesOfUser(dataset)){

            if(instance.checkAssignmentConsistencyOfUser(this)){
                numberOfConsistentAssignments ++;
            }

            numberOfTotalInstances ++;

        }

        if(numberOfTotalInstances == 0){
            // @todo may return null
            return 0.0;
        }else{
            return (double) numberOfConsistentAssignments/numberOfTotalInstances;
        }

    }

    protected ArrayList<Instance> getLabeledInstancesOfUser(Dataset dataset){
        ArrayList<Instance> labeledInstancesOfUser = new ArrayList<Instance>();
        for (Instance instance : dataset.getInstances()){
            ArrayList<Assignment> assignmentsOfUser = instance.getAssignmentsOfUser(this);
            if(!assignmentsOfUser.isEmpty()){
                labeledInstancesOfUser.add(instance);
            }
        }
        return labeledInstancesOfUser;
    }

    protected Integer getTotalNumberOfLabeledInstancesOfAllDatasets(){
        Integer totalNumberOfLabeledInstancesOfAllDatasets = 0;
        for(Dataset dataset : this.datasets){
            totalNumberOfLabeledInstancesOfAllDatasets += getTotalNumberOfInstancesLabeled(dataset, false);
        }
        return totalNumberOfLabeledInstancesOfAllDatasets;
    }

    protected Integer getTotalNumberOfLabeledUniqueInstancesOfAllDatasets(){
        Integer totalNumberOfLabeledInstancesOfAllDatasets = 0;
        for(Dataset dataset : this.datasets){
            totalNumberOfLabeledInstancesOfAllDatasets += getTotalNumberOfInstancesLabeled(dataset, true);
        }
        return totalNumberOfLabeledInstancesOfAllDatasets;
    }


    protected void printPerformanceMetrics(){
        System.out.println("\u001B[34m"+"USER PERFORMANCE METRICS"+"\u001B[0m");
        System.out.println("1. Number of Labeled Datasets:");
        System.out.println(getNumberOfDataset());
        System.out.println("2. Datasets With Completeness Percentages:");
        printComplitionsOfAllDataset();
        System.out.println("3. Total Number of Instances Labeled:");
        System.out.println(getTotalNumberOfLabeledInstancesOfAllDatasets());
        System.out.println("4. Total Number of Unique Instances Labeled:");
        System.out.println(getTotalNumberOfLabeledUniqueInstancesOfAllDatasets());
        System.out.println("5. Consistency Percentage");
        System.out.println(getConsistencyPercentageOfAllDatasets());
        System.out.println("6. Average Labeling Time");
        System.out.println(getAverageTimeSpentWhileLabeling());
        System.out.println("7. Standard Deviation of Labeling Time");
        System.out.println(getStdDevOfTimeSpentWhileLabeling());
    }

}
