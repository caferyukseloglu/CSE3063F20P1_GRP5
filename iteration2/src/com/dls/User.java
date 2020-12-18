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

    /*
    @todo not ready yet.
    */
    protected int getNumberOfAssignedDatasets(){
        return this.datasets.size();
    }

    /*
    @todo not ready yet.
     */
    protected HashMap<Dataset, Double> getDatasetCompletenessList(){
        HashMap<Dataset, Double> datasetCompletenessList = new HashMap<Dataset, Double>();
        return datasetCompletenessList;
    }

    protected int getNumberOfLabeledInstacesOfDataset(Dataset dataset){
        int totalInstances = 0;
        for (Instance instance : dataset.getInstances()){
            if(instance.checkUserAssignment(this)){
                totalInstances ++;
            }
        }
        return totalInstances;
    }

    protected int getNumberOfLabeledInstaces(){
        int totalInstances = 0;
        for (Dataset dataset : this.datasets){
            totalInstances += this.getNumberOfLabeledInstacesOfDataset(dataset);
        }
        return totalInstances;
    }


}
