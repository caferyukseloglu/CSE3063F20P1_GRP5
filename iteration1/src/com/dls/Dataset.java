package com.dls;

import java.util.ArrayList;

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
     * @param   id                  unique id number of dataset
     * @param   name                name of the dataset
     * @param   maxNumberOfLabels   maximum number of labels to assign a single instance
     * @return  nothing
     */
    public Dataset(int id, String name, int maxNumberOfLabels) {
         setId(id);
         setName(name);
         setMaxNumberOfLabels(maxNumberOfLabels);
    }
    /*
     * Sets the id of dataset
     * @param   id                  unique id number of dataset
     * @return  nothing
     */
    private void setId(int id){
        this.id = id;
        // @todo Check if it used before
    }
    /*
     * Sets the name of dataset
     * @param   name                name of dataset to set
     * @return  nothing
     */
    private void setName(String name){
        this.name = name;
    }
    /*
     * Sets the maximum number of labels to asing a single instance
     * @param   maxNumberOfLabels   Maximum number of label for a single instance
     * @return  nothing
     */
    private void setMaxNumberOfLabels(int maxNumberOfLabels){
        this.maxNumberOfLabels = maxNumberOfLabels;
    }
    /*
     * Gets dataset id
     * @return  int                  id of dataset
     */
    protected int getId(){
        return this.id;
    }
    /*
     * Gets dataset name
     * @return  String              name of dataset
     */
    protected String getName(){
        return this.name;
    }
    /*
     * Gets maximum number of labels to assign a instance
     * @return  int                  MaxNumberOfLabels
     */
    protected int getMaxNumberOfLabels(){
        return this.maxNumberOfLabels;
    }
    /*
     * Returns list of instances of dataset
     * This function is used during the JSON reading process.
     * @return  ArrayList<Instance> Array List of <Instance> objects
     */
    protected ArrayList<Instance> getInstances(){
        return this.instances;
    }
    /*
     * Returns list of labels of dataset
     * This function is used during the JSON reading process.
     * @return  ArrayList<Label>    Array List of <Label> objects
     */
    protected ArrayList<Label> getLabels(){
        return this.labels;
    }
    /*
     * Creates a <Instance> object with its id and text then adds <Instance> object to instances list of dataset.
     * @param   id                  id of instance
     * @param   text                text of instance
     * @return  <Instance>          created <Instance> object
     */
    protected Instance addInstance(int id, String text){
        Instance instance = new Instance(id, text, this);
        this.instances.add(instance);
        return instance;
    }
    /*
     * Creates a <Label> object with its id and text then adds <Label> object to labels list of dataset.
     * This function is used during the JSON reading process.
     * @param   id                  id of label
     * @param   text                text of label
     * @return  <Label>             created <Label> object
     */
    protected Label addLabel(int id, String text){
        Label label = new Label(id, text);
        this.labels.add(label);
        return label;
    }
    /*
     * Finds and returns the <Label> object with the given text value.
     * @todo What if Label has not found?
     * @param   text                text of label
     * @return  <Label>             <Label> object
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
     * @param   int                 id of label
     * @return  <Label>             <Label> object
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
     * @return  nothing             prints detailed dataset parameters
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







}
