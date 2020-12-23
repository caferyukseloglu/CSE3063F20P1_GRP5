package com.dls;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The WriteJSON class writes dataset data to JSON file.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class WriteJSON {
    private final static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

    private Dataset dataset;
    private Config config;


    /*
     * Construct method of the WriteJSON class
     * @param   config                  config object
     * @param   dataset                 dataset object
     * @return                          nothing
     */
    public WriteJSON(Dataset dataset, Config config){

        setDataset(dataset);
        setConfig(config);
        write();

    }
    /*
     * Sets object of dataset as instance variable
     * @param   dataset                 dataset object to set
     * @return                          nothing
     */
    protected void setDataset(Dataset dataset){
        this.dataset = dataset;
    }
    /*
     * Sets object of config as instance variable
     * @param   config                  config object to set
     * @return                          nothing
     */
    protected void setConfig(Config config){
        this.config = config;
    }
    /*
     * Gets dataset object
     * @return                          dataset object
     */
    protected Dataset getDataset(){
        return this.dataset;
    }
    /*
     * Gets config object
     * @return                          config object
     */
    protected Config getConfig(){
        return this.config;
    }
    /*
     * This method writes variables of given dataset, into a JSON file with simple.json library.
     * Output path of this method is defined inside config object as outputPath
     * @return                          nothing
     */
    protected void write() {

        // Main JSONObject object created
        JSONObject mainJSON = new JSONObject();
        JSONObject userPerformansJSON = new JSONObject();
        JSONObject datasetPerdormansJSON = new JSONObject();
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
//                    System.out.println(label.getId());
                    assignmentLabelsArray.add(label.getId());
                }
                singleAssignmentJSON.put("class label ids", assignmentLabelsArray);
                singleAssignmentJSON.put("instance id", instance.getId());

                assignmentsJSON.add(singleAssignmentJSON);
            }

        }
        mainJSON.put("instances", instancesJSON);
        mainJSON.put("class label assignments", assignmentsJSON);

        System.out.println("Writing process completed successfully.");
        logger.info(String.valueOf(assignmentsJSON));
        userPerformansJSON.put("1. Number of Labeled Datasets:",config.getActiveUser().getNumberOfDataset());
        //userPerformansJSON.put("2. Datasets With Completeness Percentages:",config.getActiveUser().printComplitionsOfAllDataset(););
        userPerformansJSON.put("3. Total Number of Instances Labeled:",config.getActiveUser().getTotalNumberOfLabeledInstancesOfAllDatasets());
        userPerformansJSON.put("4. Total Number of Unique Instances Labeled:",config.getActiveUser().getTotalNumberOfLabeledUniqueInstancesOfAllDatasets());
        userPerformansJSON.put("5. Consistency Percentage",config.getActiveUser().getConsistencyPercentageOfAllDatasets());
        userPerformansJSON.put("6. Average Labeling Time",config.getActiveUser().getAverageTimeSpentWhileLabeling());
        userPerformansJSON.put("7. Standard Deviation of Labeling Time",config.getActiveUser().getStdDevOfTimeSpentWhileLabeling());
        //
        //
        //
        datasetPerdormansJSON.put("1. Completion Percentages:",dataset.getCompletionPercentage());
        //datasetPerdormansJSON.put("2. Final Label Percentage:",dataset.printFinalLabelPercentages());
        //datasetPerdormansJSON.put("3. List of Unique Instances With Related Labels:")
        try {
            FileWriter file = new FileWriter(this.config.getOutputPath());
            FileWriter file2 = new FileWriter("Report.json");
            file.write(mainJSON.toJSONString());
            file2.write(userPerformansJSON.toJSONString());
            file.flush();
            file2.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
