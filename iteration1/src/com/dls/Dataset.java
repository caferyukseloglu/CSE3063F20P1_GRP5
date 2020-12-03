package com.dls;

import java.util.ArrayList;

/**
 * The Dataset class is main class of the project. It stores all other objects and instances.
 * @todo check read and write json input
 * @todo push github
 */
public class Dataset {


    private int id;
    private String name;
    private int maxNumberOfLabels;

    protected ArrayList<Label> labels = new ArrayList<Label>(); // Make it limited
    protected ArrayList<User> users = new ArrayList<User>();
    protected ArrayList<Instance> instances = new ArrayList<Instance>();

    /*
     * Construct method of the Dataset class
     * @param   id                  unique id number of dataset
     * @param   name                name of the dataset
     * @param   maxNumberOfLabels   maximum namber of labels to assign a single instance
     * @return                      initializes the object
     */
    public Dataset(int id, String name, int maxNumberOfLabels) {

         setId(id);
         setName(name);
         setMaxNumberOfLabels(maxNumberOfLabels);

    }
    /*
     * Sets the id of dataset
     * @param   id                  unique id number of dataset
     * @return                      sets id
     */
    private void setId(int id){
        this.id = id;
        // @todo Check if it used before
    }
    /*
     * Sets the name of dataset
     * @param   name                name of dataset to set
     * @return                      sets name
     */
    private void setName(String name){
        this.name = name;
    }
    /*
     * Sets the maximum number of labels to asing a single instance
     * @param   maxNumberOfLabels   Maximum number of label for a single instance
     * @return                      sets maxNumberOfLabels
     */
    private void setMaxNumberOfLabels(int maxNumberOfLabels){
        this.maxNumberOfLabels = maxNumberOfLabels;
    }
    /*
     * Creates a <Instance> object with its id and text then adds <Instance> object to instances list of dataset.
     * @param   id                  id of instance
     * @param   text                text of instance
     * @return                      Created <Instance> object
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
     * @return                      (void)
     */
    protected void addLabel(int id, String text){
        Label label = new Label(id, text);
        this.labels.add(label);
    }
    /*
     * Returns <Instance> object by given instance id
     * @param   <Instance>          <Instance> object to get index id
     * @return                      index id of given object
     */
    protected int getIdOfInstances(Instance instance){

        return this.instances.indexOf(instance);

    }
    /*
     * Returns list of instances of dataset
     * This function is used during the JSON reading process.
     * @return                      Array List of <Instance> objects
     */
    protected ArrayList<Instance> getInstances(){

        return this.instances;

    }
    /*
     * Finds and returns the <Label> object with the given text value.
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

    protected Label getLabelById(int id){
        for(Label label : this.labels){
            if(label.getId() == id){
                return label;
            }
        }
        return new Label(id, "");
    }

    protected void printLabels(){
        for(Label label : this.labels){
            System.out.println("ID: "+label.getId() + " Text:" + label.getText());
        }
    }

    protected void printInstances(){
        for(Instance instance : this.instances){
            System.out.println("ID: "+instance.getId() + " Text:" + instance.getText());
        }
    }

    protected void printDatasetDetailed(){
        for(Instance instance : this.instances){
            System.out.println("ID: "+instance.getId() + " Text:" + instance.getText());
            for(Assignment assignment : instance.getAssignments()){
                System.out.println("---Assignment ID: "+assignment.getId()+" Username: "+assignment.getUser().getName());
                for(Label label : assignment.getLabels()){
                    System.out.println("------Label: "+label.getText());
                }
            }
        }
    }

    protected int getId(){
        return this.id;
    }

    protected String getName(){
        return this.name;
    }

    protected int getMaxNumberOfLabels(){
        return this.maxNumberOfLabels;
    }

    protected ArrayList<Label> getLabelList(){
        return this.labels;
    }

    protected ArrayList<Instance> getInstanceList(){
        return this.instances;
    }


}
