package com.dls;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The User class stores variables and methods for each user that stated initially.
 * @version iteration-2.0
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
    public User(int id, String name, String type, String password,Double consistencyCheckProbability){

        setId(id);
        setName(name);
        setType(type);
        setPassword(password);
        setConsistencyCheckProbability(consistencyCheckProbability);
        System.out.println("User created.");

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
    public int getId(){
        return this.id;
    }
    /*
     * Gets type of user
     * @return                          user type
     */
    public String getType(){
        return this.type;
    }
    /*
     * Gets name of user
     * @return                          user name
     */

    public String getName(){
        return this.name;
    }
    /*
     * Gets password of user
     * @return                          user password
     */
    public String getPassword(){
        return this.password;
    }
    /*
     * Gets consistency check probability of user
     * @return                          user consistency check probability
     */
    public Double getConsistencyCheckProbability(){
        return  this.consistencyCheckProbability;
    }
    /*
     * Checks user name and password.
     * @return                          if credentials correct
     */
    public Boolean checkPassword(String userName, String password){
        return this.getName().equals(userName) && this.getPassword().equals(password);
    }
    /*
     * Updates user name
     * @return                          nothing
     */
    public void updateName(String name){
        this.name = name;
    }
    /*
     * Updates user type
     * @return                          nothing
     */
    public void updateType(String type){
        this.type = type;
    }
    /*
     * Adds dataset to datasets list
     * @param   dataset                 Dataset object
     * @return                          nothing
     */
    public void addDataset(Dataset dataset){
        if(!this.datasets.contains(dataset)){
            this.datasets.add(dataset);
            System.out.println("Dataset assigned to user.");
        }
    }
    /*
     * Adds assignment to assignments list
     * @param   assignment              Assignment object
     * @return                          nothing
     */
    public void addAssignment(Assignment assignment){
        if(!this.assignments.contains(assignment)){
            this.assignments.add(assignment);
        }
    }
    /*
     * Calculates total number of instances labeled within all the datasets assigned by this user.
     * If unique parameter is True, It also compare all the instances' uniqueness.
     * @param   dataset                 Dataset object
     * @param   unique                  unique instances or not
     * @return                          nothing
     */
    public Integer getTotalNumberOfInstancesLabeled(Dataset dataset, Boolean unique){
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
    /*
     * Gets total number of datasets
     * @return                          total number of datasets
     */
    public Integer getNumberOfDataset(){
        return this.datasets.size();
    }
    /*
     * Calculates completeness percentage of all datasets and returns HashMap
     * @return                          HashMap of dataset and its completeness percentage distribution
     */
    public HashMap<Dataset, Double> getCompletionsOfAllDataset(){
        HashMap<Dataset, Double> completionsOfAllDataset = new HashMap<Dataset, Double>();
        for(Dataset dataset : this.datasets){
            completionsOfAllDataset.put(dataset, getCompletionOfDataset(dataset));
        }
        return completionsOfAllDataset;
    }
    /*
     * Calculates completeness percentage of given datasets and returns percentage
     * @return                          completeness percentage of dataset
     */
    public Double getCompletionOfDataset(Dataset dataset){
        Integer labeledInstanceNumberOfDataset = this.getTotalNumberOfInstancesLabeled(dataset, false);
        Integer instanceNumber = dataset.getNumberOfInstances();
        return (double) labeledInstanceNumberOfDataset/ instanceNumber;
    }
    /*
     * Prints getCompletionOfDataset method
     * @return                          nothing
     */
    public void printCompletionsOfDataset(Dataset dataset){
        Double percentage = this.getCompletionOfDataset(dataset) * 100.0;
        System.out.println("Completeness Percentage of "+dataset.getName()+" : "+percentage.intValue()+" %");
    }
    /*
     * Repeats printCompletionsOfDataset method for each dataset
     * @return                          nothing
     */
    public void printCompletionsOfAllDataset(){
        for(Dataset dataset : this.datasets){
            this.printCompletionsOfDataset(dataset);
        }
    }
    /*
     * Sets loginDatetime for user
     * @return                          nothing
     */
    public void setLoginDatetime(){
        this.loginDatetime = new Datetime();
        this.logoutDatetime = this.loginDatetime;
    }
    /*
     * Sets logoutDatetime for user
     * @return                          nothing
     */
    public void setLogoutDatetime(){
        this.logoutDatetime = new Datetime();
    }
    /*
     * Calculate average time spent while labeling
     * @deprecated
     * @see getAverageTimeSpentWhileLabeling
     * @return                          nothing
     */
    public Double getAverageTimeSpentInLabelingOld(Dataset dataset){
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
    /*
     * Calculate average time spent while labeling
     * @return                          Average time spent
     */
    public Double getAverageTimeSpentWhileLabeling(){
        Integer totalNumberOfInstancesLabeled = this.assignments.size();
        Double totalTimeSpent = 0.0;
        totalTimeSpent += java.time.Duration.between(this.loginDatetime.getDatetime(), this.assignments.get(0).getDatetime().getDatetime()).getSeconds();
        for(Integer i = 1; i < totalNumberOfInstancesLabeled ; i ++){
            totalTimeSpent += java.time.Duration.between(this.assignments.get(i-1).getDatetime().getDatetime(), this.assignments.get(i).getDatetime().getDatetime()).getSeconds();
        }
        return totalTimeSpent / totalNumberOfInstancesLabeled.doubleValue();
    }
    /*
     * Calculate standard deviation of time spent while labeling
     * @return                          standard deviation of time spent while labeling
     */
    public Double getStdDevOfTimeSpentWhileLabeling(){
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
    /*
     * Get consistency percentages of all datasets.
     * @return                          consistency percentage
     */
    public Double getConsistencyPercentageOfAllDatasets(){
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
    /*
     * Calculate consistency percentage of given dataset
     * @return                          consistency percentage of dataset
     */
    public Double getConsistencyPercentageOfDataset(Dataset dataset){
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
    /*
     * Gets all the instances which labeled by the user of given dataset.
     * @return                          list of instances
     */
    public ArrayList<Instance> getLabeledInstancesOfUser(Dataset dataset){
        ArrayList<Instance> labeledInstancesOfUser = new ArrayList<Instance>();
        for (Instance instance : dataset.getInstances()){
            ArrayList<Assignment> assignmentsOfUser = instance.getAssignmentsOfUser(this);
            if(!assignmentsOfUser.isEmpty()){
                labeledInstancesOfUser.add(instance);
            }
        }
        return labeledInstancesOfUser;
    }
    /*
     * Gets total number of labeled instances of all the datasets
     * @return                          total number of labeled instances
     */
    public Integer getTotalNumberOfLabeledInstancesOfAllDatasets(){
        Integer totalNumberOfLabeledInstancesOfAllDatasets = 0;
        for(Dataset dataset : this.datasets){
            totalNumberOfLabeledInstancesOfAllDatasets += getTotalNumberOfInstancesLabeled(dataset, false);
        }
        return totalNumberOfLabeledInstancesOfAllDatasets;
    }
    /*
     * Gets total number of unique instances labeled by user
     * @return                          unique instances list
     */
    public Integer getTotalNumberOfLabeledUniqueInstancesOfAllDatasets(){
        Integer totalNumberOfLabeledInstancesOfAllDatasets = 0;
        for(Dataset dataset : this.datasets){
            totalNumberOfLabeledInstancesOfAllDatasets += getTotalNumberOfInstancesLabeled(dataset, true);
        }
        return totalNumberOfLabeledInstancesOfAllDatasets;
    }
    /*
     * Prints user performance metrics
     * @return                          nothing
     */
    public void printPerformanceMetrics(){
        System.out.println("\u001B[34m"+"USER PERFORMANCE METRICS"+"\u001B[0m");
        System.out.println("1. Number of Labeled Datasets:");
        System.out.println(getNumberOfDataset());
        System.out.println("2. Datasets With Completeness Percentages:");
        printCompletionsOfAllDataset();
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
