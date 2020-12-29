package com.dls;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The WriteJSON class writes dataset data to JSON file.
 * @version iteration-2.0
 * @since 2020-12-01
 */
public class WriteJSON {

    private final static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
    private Dataset dataset;
    private Config config;
    private Instance instance;

    /*
     * Construct method of the WriteJSON class
     * @param   config                  config object
     * @param   dataset                 dataset object
     * @return                          nothing
     */
    public WriteJSON(Dataset dataset, Config config, Instance instance){

        setDataset(dataset);
        setConfig(config);
        setInstance(instance);
        write();

    }



    /*
     * Sets object of dataset as instance variable
     * @param   dataset                 dataset object to set
     * @return                          nothing
     */
    public void setDataset(Dataset dataset){
        this.dataset = dataset;
    }
    /*
     * Sets object of config as instance variable
     * @param   config                  config object to set
     * @return                          nothing
     */
    public void setConfig(Config config){
        this.config = config;
    }
    /*
     * Gets dataset object
     * @return                          dataset object
     */
    public void setInstance(Instance instance){this.instance = instance;}
    public Dataset getDataset(){
        return this.dataset;
    }
    /*
     * Gets config object
     * @return                          config object
     */
    public Config getConfig(){
        return this.config;
    }
    /*
     * This method writes variables of given dataset, into a JSON file with simple.json library.
     * Output path of this method is defined inside config object as outputPath
     * @return                          nothing
     */
    public void write() {
        System.out.println("\u001B[31m"+"Writing JSON FILE"+"\u001B[0m");
        // Main JSONObject object created
        JSONObject mainJSON = new JSONObject();
        JSONObject userPerformansJSON = new JSONObject();
        JSONObject datasetPerdormansJSON = new JSONObject();
        JSONObject instancePerformansJSON = new JSONObject();
        // First level keys and their values
        mainJSON.put("dataset id", this.dataset.getId());
        mainJSON.put("dataset name", this.dataset.getName());
        mainJSON.put("maximum number of labels per instance", this.dataset.getMaxNumberOfLabels());


        // Sub-arrays
        JSONArray labelsJSON = new JSONArray();
        JSONArray instancesJSON = new JSONArray();
        JSONArray assignmentsJSON = new JSONArray();

        // Labels
        for (Label label : this.dataset.getLabels()) {
            JSONObject singleLabelJSON = new JSONObject();
            singleLabelJSON.put("label text", label.getText());
            singleLabelJSON.put("label id", label.getId());

            labelsJSON.add(singleLabelJSON);
        }
        mainJSON.put("class labels", labelsJSON);

        // Instances and Assignments
        for (Instance instance : this.dataset.getInstances()){
            JSONObject singleInstanceJSON = new JSONObject();
            singleInstanceJSON.put("id", instance.getId());
            singleInstanceJSON.put("instance", instance.getText());
            instancesJSON.add(singleInstanceJSON);

            for(Assignment assignment : instance.getAssignments()){
                JSONObject singleAssignmentJSON = new JSONObject();
                singleAssignmentJSON.put("datetime", assignment.getDatetime().getDatetimeText());
                singleAssignmentJSON.put("user id", assignment.getUser().getId());

                JSONArray assignmentLabelsArray = new JSONArray();
                for(Label label : assignment.getLabels()){
                    assignmentLabelsArray.add(label.getId());
                }
                singleAssignmentJSON.put("class label ids", assignmentLabelsArray);
                singleAssignmentJSON.put("instance id", instance.getId());

                assignmentsJSON.add(singleAssignmentJSON);
            }
        }
        mainJSON.put("instances", instancesJSON);
        mainJSON.put("class label assignments", assignmentsJSON);

        logger.info(String.valueOf(assignmentsJSON));

        userPerformansJSON.put("1. Number of Labeled Datasets:",config.getActiveUser().getNumberOfDataset());
        userPerformansJSON.put("2. Datasets With Completeness Percentages:",(config.getActiveUser().getCompletionOfDataset(dataset) * 100.0));
        userPerformansJSON.put("3. Total Number of Instances Labeled:",config.getActiveUser().getTotalNumberOfLabeledInstancesOfAllDatasets());
        userPerformansJSON.put("4. Total Number of Unique Instances Labeled:",config.getActiveUser().getTotalNumberOfLabeledUniqueInstancesOfAllDatasets());
        userPerformansJSON.put("5. Consistency Percentage",config.getActiveUser().getConsistencyPercentageOfAllDatasets());
        userPerformansJSON.put("6. Average Labeling Time",config.getActiveUser().getAverageTimeSpentWhileLabeling());
        userPerformansJSON.put("7. Standard Deviation of Labeling Time",config.getActiveUser().getStdDevOfTimeSpentWhileLabeling());

        JSONArray datasetFinalLabel=new JSONArray();
        JSONArray datasetUnigueInstance = new JSONArray();
        JSONArray datasetUserCompletions = new JSONArray();
        JSONArray datasetUserConsistency = new JSONArray();

        HashMap<Label, Double> finalLabelPercentages = dataset.getFinalLabelPercentages();
        for(Map.Entry<Label, Double> entry : finalLabelPercentages.entrySet()){
            datasetFinalLabel.add(entry.getKey().getText()+" => "+entry.getValue());
        }

        HashMap<Label, ArrayList<Instance>> labelInstanceList = dataset.getLabelInstanceList();
        for(Map.Entry<Label, ArrayList<Instance>> entry : labelInstanceList.entrySet()){
            String row = entry.getKey().getText()+" => ";
            for(Instance instance : entry.getValue()){
                row = row + instance.getId() + ",";
            }
            datasetUnigueInstance.add(row);
        }
        HashMap<User, Double> userConsistencyPercentages = dataset.getUserConsistencyPercentages();
        for (Map.Entry<User, Double> entry : userConsistencyPercentages.entrySet())
        { Double percentage = entry.getValue()*100;
            datasetUserConsistency.add(entry.getKey().getName()+" : "+percentage.intValue()+" %"); }

        HashMap<User, Double> userCompletions = dataset.getUserCompletionsPercentages();
        for (Map.Entry<User, Double> entry : userCompletions.entrySet())
        {
            Double percentage = entry.getValue()*100;
            datasetUserCompletions.add(entry.getKey().getName()+" : "+percentage.intValue()+" %");
        }

        datasetPerdormansJSON.put("1. Completion Percentages:",dataset.getCompletionPercentage());
        datasetPerdormansJSON.put("2. Final Label Percentage:",datasetFinalLabel);
        datasetPerdormansJSON.put("3. List of Unique Instances With Related Labels:",datasetUnigueInstance);
        datasetPerdormansJSON.put("4. Number of User Assigned",dataset.getNumberOfUsers());
        datasetPerdormansJSON.put("5. List of Users Assigned and Their Completeness Percentages",datasetUserCompletions);
        datasetPerdormansJSON.put("6. List of Users Assigned and Their Consistency Percentages",datasetUserConsistency);

        JSONArray instanceMostF =new JSONArray();
        JSONArray instanceLabelP = new JSONArray();

        HashMap<Label, Double> mostFrequentLabel = instance.getTheMostFrequentLabel();
        for (Map.Entry<Label, Double> entry : mostFrequentLabel.entrySet()){
            instanceMostF.add(entry.getKey().getText() + " => " + entry.getValue() + "%");
        }
        HashMap<Label, Double> labelPercentages = instance.getLabelPercentages();
        for (Map.Entry<Label, Double> entry : labelPercentages.entrySet()){
            instanceLabelP.add(entry.getKey().getText() + " => " + entry.getValue() + "%");
        }

        instancePerformansJSON.put("1. Number of Assignments",instance.getAssignments().size());
        instancePerformansJSON.put("2. Number of Unique Assignments",instance.getNumberOfUniqueAssignments());
        instancePerformansJSON.put("3. Number of Unique Users",instance.getNumberOfUsers());
        instancePerformansJSON.put("4. Most Frequent Label and Its Percentage",instanceMostF);
        instancePerformansJSON.put("5. List of Labels and Their Percentages",instanceLabelP);
        instancePerformansJSON.put("6. Entropy of the Labels",instance.getLabelDistributionEntropy());

        try {
            FileWriter file = new FileWriter(this.config.getOutputPath());
            FileWriter file2 = new FileWriter("User_Report.json");
            FileWriter file3= new FileWriter("Dataset_Report.json");
            FileWriter file4 = new FileWriter("Instance_Report.json");
            file.write(mainJSON.toJSONString());
            file2.write(userPerformansJSON.toJSONString());
            file3.write(datasetPerdormansJSON.toJSONString());
            file4.write(instancePerformansJSON.toJSONString());
            file.flush();
            file2.flush();
            file3.flush();
            file4.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\u001B[31m"+"Writing JSON File Process Completed!"+"\u001B[0m");
    }
}
