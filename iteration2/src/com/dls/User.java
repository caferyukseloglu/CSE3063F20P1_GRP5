package com.dls;

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
    private ArrayList<Dataset> datasets = new ArrayList<Dataset>();

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

    protected int getTotalNumberOfInstancesLabeled(Dataset dataset, Boolean unique){
        int number = 0;
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

    protected int getNumberOfDataset(){
        return this.datasets.size();
    }

    protected String getCompletenessPercentageOfDatasets(){
        String completenessOfDatasets = " ";
        for(Dataset dataset : this.datasets){
            double labeledInstanceNumberOfDataset = this.getTotalNumberOfInstancesLabeled(dataset, false);
            double instanceNumber = dataset.getIntanceNumber();
            String completeness =  String.valueOf(labeledInstanceNumberOfDataset/ instanceNumber * 100);
            completenessOfDatasets = completenessOfDatasets + "\"" + dataset.getName() + "\": " + completeness+",";
        }
        return completenessOfDatasets;
    }

    protected Double getCompletenessPercentageOfDataset(Dataset dataset){
        double labeledInstanceNumberOfDataset = this.getTotalNumberOfInstancesLabeled(dataset, false);
        double instanceNumber = dataset.getIntanceNumber();
        return labeledInstanceNumberOfDataset/ instanceNumber * 100;
    }

    protected void setLoginDatetime(){
        this.loginDatetime = new Datetime();
        this.logoutDatetime = this.loginDatetime;
    }

    protected void setLogoutDatetime(){
        this.logoutDatetime = new Datetime();
    }

    protected double getAverageTimeSpentInLabeling(Dataset dataset){
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


}
