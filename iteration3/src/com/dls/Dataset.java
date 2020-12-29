package com.dls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Dataset class is main class of the project. It stores all other objects and instances.
 * @version iteration-2.0
 * @since 2020-12-01
 *
 */
public class Dataset {
    
    private Integer id;
    private String name;
    private Integer maxNumberOfLabels;
    private String inputPath;
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
    public Dataset(int id, String name, int maxNumberOfLabels, String inputPath) {
         setId(id);
         setName(name);
         setMaxNumberOfLabels(maxNumberOfLabels);
         //this.consistencyCheckProbability = consistencyCheckProbability;
         this.inputPath = inputPath;
         //@todo set input path method
    }
    /*
     * Sets the id of dataset
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
    public void setMaxNumberOfLabels(int maxNumberOfLabels){
        this.maxNumberOfLabels = maxNumberOfLabels;
    } 
    /*
     * Gets dataset id
     * @return                          id of dataset
     */
    public Integer getId(){
        return this.id;
    }
    /*
     * Gets dataset name
     * @return                          name of dataset
     */
    public String getName(){
        return this.name;
    }
    /*
     * Gets maximum number of labels to assign a instance
     * @return                          MaxNumberOfLabels
     */
    public int getMaxNumberOfLabels(){
        return this.maxNumberOfLabels;
    }
    /*
     * Returns list of instances of dataset
     * This function is used during the JSON reading process.
     * @return                          Array List of <Instance> objects
     */
    public ArrayList<Instance> getInstances(){
        return this.instances;
    }
    /*
     * Returns list of labels of dataset
     * This function is used during the JSON reading process.
     * @return                          Array List of <Label> objects
     */
    public ArrayList<Label> getLabels(){
        return this.labels;
    }
    /*
     * Finds and returns the <Label> object with the given text value.
     * @todo What if Label has not found?
     * @param   text                    text of label
     * @return                          <Label> object
     */
    public Label getLabelByText(String text){
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
    public Label getLabelById(int id){
        for(Label label : this.labels){
            if(label.getId() == id){
                return label;
            }
        }
        return new Label(id, "");
    }
    /*
     * Returns InputPath
     * @return                          input path
     */
    public String getInputPath(){
        return this.inputPath;
    }
    /*
     * Returns user completions of the dataset object
     * @return                          completeness percentage
     */
    public Float getUserCompletions(){
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
    /*
     * Returns label frequencies of the dataset object
     * @return                          labelFrequencies HashMap object
     */
    public HashMap<Label, Double> getLabelFrequencies(){
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
        Integer totalNumberOfLabels = this.getNumberOfInstances();
        for (Map.Entry<Label, Double> entry : labelFrequencies.entrySet()) {
            labelFrequencies.replace(entry.getKey(), entry.getValue()/totalNumberOfLabels*100);
        }
        return labelFrequencies;
    }
    /*
     * Returns total number of instances
     * @return                          total number of instances
     */
    public int getNumberOfInstances(){
        return this.instances.size();
    }
    /*
     * Returns total number of users
     * @return                          total number of users
     */
    public int getNumberOfUsers(){
        return this.users.size();
    }
    /*
     * Completion percentage of dataset
     * @return                          completeness percentage
     */
    public Double getCompletionPercentage(){
        Integer completedInstances = 0;
        for(Instance instance : this.instances){
            if(!instance.getAssignments().isEmpty()){
                completedInstances ++;
            }
        }
        return completedInstances.doubleValue() / this.instances.size() * 100.0;
    }
    /*
     * Get user completeness percentage list by user
     * @return                          completeness percentage HashMap by user
     */
    public HashMap<User, Double> getUserCompletionsPercentages(){
        HashMap<User, Double> userCompletionsPercentages = new HashMap<User, Double>();
        for(User user : this.users){
            Double complitions = user.getCompletionOfDataset(this);
            userCompletionsPercentages.put(user, complitions);
        }
        return userCompletionsPercentages;
    }
    /*
     * Get user consistency percentage list by user
     * @return                          consistency percentage HashMap by user
     */
    public HashMap<User, Double> getUserConsistencyPercentages(){
        HashMap<User, Double> userConsistencyPercentages = new HashMap<User, Double>();
        for(User user : this.users){
            Double consistency = user.getConsistencyPercentageOfDataset(this);
            userConsistencyPercentages.put(user, consistency);
        }
        return userConsistencyPercentages;
    }
    /*
     * Get instance final label percentage list by user
     * @return                           final label percentage HashMap
     */
    public HashMap<Label, Double> getFinalLabelPercentages(){
        HashMap<Label, Integer> finalLabelCounts = new HashMap<Label, Integer>();
        HashMap<Label, Double> finalLabelPercentages = new HashMap<Label, Double>();
        Integer totalNumberOfInstances = 0;
        for(Instance instance : this.instances){
            if(!instance.getAssignments().isEmpty()){
                Label finalLabel = instance.getFinalLabel();
                if(!finalLabelCounts.containsKey(finalLabel)){
                    finalLabelCounts.put(finalLabel, 1);
                }else{
                    finalLabelCounts.replace(finalLabel, finalLabelCounts.get(finalLabel)+1);
                }
                totalNumberOfInstances ++;
            }
        }
        for (Map.Entry<Label, Integer> entry : finalLabelCounts.entrySet()) {
            Double percentage = entry.getValue().doubleValue() / totalNumberOfInstances.doubleValue() * 100.0;
            finalLabelPercentages.put(entry.getKey(), percentage);
        }
        return finalLabelPercentages;
    }
    /*
     * Get Label and Instance HashMap
     * @return                          Label and Instance HashMap
     */
    public HashMap<Label, ArrayList<Instance>> getLabelInstanceList(){
        HashMap<Label, ArrayList<Instance>> labelInstanceList = new HashMap<Label, ArrayList<Instance>>();
        for(Instance instance : this.instances){
            for(Label label : instance.getUniqueLabels()){
                if(labelInstanceList.containsKey(label)){
                    labelInstanceList.get(label).add(instance);
                }else{
                    ArrayList<Instance> instances = new ArrayList<Instance>();
                    instances.add(instance);
                    labelInstanceList.put(label, instances);
                }
            }
        }
        return labelInstanceList;
    }
    /*
     * Creates a <Instance> object with its id and text then adds <Instance> object to instances list of dataset.
     * @param   id                      id of instance
     * @param   text                    text of instance
     * @return                          created <Instance> object
     */
    public Instance addInstance(int id, String text){
        System.out.println("Adding new instance to :"+this.getName()+" instance :"+text);
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
    public Label addLabel(int id, String text){
        System.out.println("\u001B[35m"+"Adding PreDefined Label:"+text+"\u001B[0m");
        Label label = new Label(id, text);
        this.labels.add(label);
        return label;
    }
    /*
     * Adds user to users list
     * @return                          nothing
     */
    public void addUser(User user){
        System.out.println("\u001B[35m"+"Adding PreDefined User:"+user.getName()+"\u001B[0m");
        if(!this.users.contains(user)){
            this.users.add(user);
        }
    }
    /*
     * Prints detailed dataset parameters.
     * This method is useful for development purposes.
     * @return                          nothing
     */
    public void printDatasetDetailed(){
        for(Instance instance : this.instances){
            System.out.println("ID: "+instance.getId() + " Text:" + instance.getText());
            for(Assignment assignment : instance.getAssignments()){
                System.out.println("---Instance ID: "+assignment.getInstance().getId()+" Username: "+assignment.getUser().getName());
                for(Label label : assignment.getLabels()){
                    System.out.println("------Label: "+label.getText());
                }
            }
        }
    }
    /*
     * Prints getUserCompletionsPercentages method
     * @return                          nothing
     */
    public void printUserCompletions(){
        HashMap<User, Double> userCompletions = this.getUserCompletionsPercentages();
        for (Map.Entry<User, Double> entry : userCompletions.entrySet())
        {
            Double percentage = entry.getValue()*100;
            System.out.println(entry.getKey().getName()+" : "+percentage.intValue()+" %");
        }
    }
    /*
     * Prints getUserConsistencyPercentages method
     * @return                          nothing
     */
    public void printUserConsistencies(){
        HashMap<User, Double> userConsistencyPercentages = this.getUserConsistencyPercentages();
        for (Map.Entry<User, Double> entry : userConsistencyPercentages.entrySet())
        {
            Double percentage = entry.getValue()*100;
            System.out.println(entry.getKey().getName()+" : "+percentage.intValue()+" %");
        }
    }
    /*
     * Prints getLabelFrequencies method
     * @return                          nothing
     */
    public void printLabelFrequencies(){
        HashMap<Label, Double> labelFrequencies = getLabelFrequencies();
        for(Map.Entry<Label, Double> entry : labelFrequencies.entrySet()){
            System.out.println(entry.getKey().getText()+" => "+entry.getValue());
        }
    }
    /*
     * Prints userList
     * @return                          nothing
     */
    public void printUserList(){
        for(User user : this.users){
            System.out.println(user.getName());
        }
    }
    /*
     * Prints getFinalLabelPercentages method
     * @return                          nothing
     */
    public void printFinalLabelPercentages(){
        HashMap<Label, Double> finalLabelPercentages = getFinalLabelPercentages();
        for(Map.Entry<Label, Double> entry : finalLabelPercentages.entrySet()){
            System.out.println(entry.getKey().getText()+" => "+entry.getValue());
        }
    }
    /*
     * Prints getLabelInstanceList method
     * @return                          nothing
     */
    public void printLabelInstanceList(){
        HashMap<Label, ArrayList<Instance>> labelInstanceList = getLabelInstanceList();
        for(Map.Entry<Label, ArrayList<Instance>> entry : labelInstanceList.entrySet()){
            String row = entry.getKey().getText()+" => ";
            for(Instance instance : entry.getValue()){
                row = row + instance.getId() + ",";
            }
            System.out.println(row);
        }
    }
    /*
     * Prints all performance metrics
     * @return                          nothing
     */
    public void printPerformanceMetrics(){
        System.out.println("\u001B[34m"+"DATASET PERFORMANCE METRICS"+"\u001B[0m");
        System.out.println("1. Completion Percentages:");
        System.out.println(getCompletionPercentage());
        System.out.println("2. Final Label Percentage:");
        printFinalLabelPercentages();
        System.out.println("3. List of Unique Instances With Related Labels:");
        printLabelInstanceList(); //@todo check if unique
        System.out.println("4. Number of User Assigned");
        System.out.println(getNumberOfUsers());
        System.out.println("5. List of Users Assigned and Their Completeness Percentages");
        printUserCompletions();
        System.out.println("5. List of Users Assigned and Their Consistency Percentages");
        printUserConsistencies();
    }
}
