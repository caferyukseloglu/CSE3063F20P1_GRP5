package com.dls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Dataset class is main class of the project. It stores all other objects and instances.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Dataset {


    private int id;
    private String name;
    private int maxNumberOfLabels;
    private ArrayList<Label> labels = new ArrayList<Label>(); // Make it limited
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Instance> instances = new ArrayList<Instance>();

    /*
     * Construct method of the Dataset class
     * @param   id                      unique id number of dataset
     * @param   name                    name of the dataset
     * @param   maxNumberOfLabels       maximum number of labels to assign a single instance
     * @return                          nothing
     */
    public Dataset(int id, String name, int maxNumberOfLabels) {
         setId(id);
         setName(name);
         setMaxNumberOfLabels(maxNumberOfLabels);
    }
    /*
     * Sets the id of dataset
     * @todo Check if it used before
     * @param   id                      unique id number of dataset
     * @return                          nothing
     */
    private void setId(int id){
        this.id = id;
    }
    /*
     * Sets the name of dataset
     * @param   name                    name of dataset to set
     * @return                          nothing
     */
    private void setName(String name){
        this.name = name;
    }
    /*
     * Sets the maximum number of labels to asing a single instance
     * @param   maxNumberOfLabels       maximum number of label for a single instance
     * @return                          nothing
     */
    private void setMaxNumberOfLabels(int maxNumberOfLabels){
        this.maxNumberOfLabels = maxNumberOfLabels;
    }
    /*
     * Gets dataset id
     * @return                          id of dataset
     */
    protected int getId(){
        return this.id;
    }
    /*
     * Gets dataset name
     * @return                          name of dataset
     */
    protected String getName(){
        return this.name;
    }
    /*
     * Gets maximum number of labels to assign a instance
     * @return                          MaxNumberOfLabels
     */
    protected int getMaxNumberOfLabels(){
        return this.maxNumberOfLabels;
    }
    /*
     * Returns list of instances of dataset
     * This function is used during the JSON reading process.
     * @return                          Array List of <Instance> objects
     */
    protected ArrayList<Instance> getInstances(){
        return this.instances;
    }
    /*
     * Returns list of labels of dataset
     * This function is used during the JSON reading process.
     * @return                          Array List of <Label> objects
     */
    protected ArrayList<Label> getLabels(){
        return this.labels;
    }
    /*
     * Creates a <Instance> object with its id and text then adds <Instance> object to instances list of dataset.
     * @param   id                      id of instance
     * @param   text                    text of instance
     * @return                          created <Instance> object
     */
    protected Instance addInstance(int id, String text){
        Instance newInstance = new Instance(id, text, this);
        this.instances.add(newInstance);
        return newInstance;
    }

    /*
     * Creates a <Label> object with its id and text then adds <Label> object to labels list of dataset.
     * This function is used during the JSON reading process.
     * @param   id                      id of label
     * @param   text                    text of label
     * @return                          created <Label> object
     */
    protected Label addLabel(int id, String text){
        Label label = new Label(id, text);
        this.labels.add(label);
        return label;
    }
    /*
     * Finds and returns the <Label> object with the given text value.
     * @todo What if Label has not found?
     * @param   text                    text of label
     * @return                          <Label> object
     */
    protected Label getLabelByText(String text){
        for(Label label : this.labels){
            if(label.getText().equals(text)){
                return label;
            }
        }
        return new Label(0, text);
    }
    /*
     * Finds and returns the <Label> object with the given id value.
     * @todo What if Label has not found?
     * @param   int                     id of label
     * @return                          <Label> object
     */
    protected Label getLabelById(int id){
        for(Label label : this.labels){
            if(label.getId() == id){
                return label;
            }
        }
        return new Label(id, "");
    }
    /*
     * Prints detailed dataset parameters.
     * This method is useful for development purposes.
     * @return                          nothing
     */
    protected void printDatasetDetailed(){
        for(Instance instance : this.instances){
            System.out.println("ID: "+instance.getId() + " Text:" + instance.getText());
            for(Assignment assignment : instance.getAssignments()){
                System.out.println("---Instance ID: "+assignment.getInstanceId()+" Username: "+assignment.getUser().getName());
                for(Label label : assignment.getLabels()){
                    System.out.println("------Label: "+label.getText());
                }
            }
        }
    }

    protected int getIntanceNumber(){
        return this.instances.size();
    }


    protected Float getCompletenessPercentage(){
        Integer numberOfInstances = this.instances.size();
        Integer numberOfInstancesWithLabel = 0;
        for(Instance instance : this.instances){
            if(!instance.getAssignments().isEmpty()){
                numberOfInstancesWithLabel += 1;
            }
            numberOfInstances += 1;
        }

        return numberOfInstancesWithLabel.floatValue()/numberOfInstances.floatValue()*100;

    }

    protected void getLabelFrequencies(){
        HashMap<Label, Double> labelFrequencies = new HashMap<Label, Double>();
        for(Instance instance : this.instances){
            if(instance.getFinalLabel() != null){
                Label label = instance.getFinalLabel();
                if(labelFrequencies.containsKey(label)){
                    labelFrequencies.replace(label, labelFrequencies.get(label)+1.0);
                }else{
                    labelFrequencies.put(label, 1.0);
                }
            }
        }
        Map.Entry<Label, Double> maxEntry = null;
        Integer totalNumberOfLabels = this.getIntanceNumber();
        for (Map.Entry<Label, Double> entry : labelFrequencies.entrySet())
        {
            labelFrequencies.replace(entry.getKey(), entry.getValue()/totalNumberOfLabels*100);
        }


        for(Map.Entry<Label, Double> entry : labelFrequencies.entrySet()){

           System.out.println(entry.getKey().getText()+" => "+entry.getValue());

        }

    }

    protected void printUserList(){

        for(User user : this.users){
            System.out.println(user.getName());
        }

    }

    protected int getNumberOfUsers(){
        return this.users.size();
    }


    protected HashMap<User, Double> getUserCompletenessPercentages(){
        HashMap<User, Double> userCompletenessPercentages = new HashMap<User, Double>();
        for(User user : this.users){
            Double completeness = user.getCompletenessPercentageOfDataset(this);
            userCompletenessPercentages.put(user, completeness);
        }
        return userCompletenessPercentages;
    }



}
