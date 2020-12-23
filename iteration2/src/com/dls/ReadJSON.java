package com.dls;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

/**
 * The ReadJSON class reads and parses given JSON file with json.simple package.
 * Then assign related values to given objects.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class ReadJSON {

    private Dataset dataset;
    private Config config;

    /*
     * Construct method of the ReadJSON class
     * @param   config                  config object
     * @return                          nothing
     */
    public ReadJSON(Config config){

        setConfig(config);

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
     * This method reads json file from inputPath instance of config object.
     * Parses this json and creates object oriented dataset.
     * Then returns created dataset object.
     * @return                          dataset object
     */
    protected Dataset readInput(){

        // JSONParser object created
        JSONParser parser = new JSONParser();
        Dataset dataset = config.getActiveDataset();
        try {

            // FileReader gets file from given path
            Object jsonParser = parser.parse(new FileReader(dataset.getInputPath()));
            JSONObject jsonObject = (JSONObject) jsonParser;

            // JSONParser parses with given json keys then assigns them to JSONArray
            JSONArray instanceList = (JSONArray) jsonObject.get("instances");
            JSONArray labelList = (JSONArray) jsonObject.get("class labels");

            int maxNumberOfLabels = Integer.parseInt(jsonObject.get("maximum number of labels per instance").toString());

            // Dataset created with JSON values.
            dataset.setMaxNumberOfLabels(maxNumberOfLabels);
            setDataset(dataset);

            // Parsing methods called for sub-arrays of Instances and Labels
            parseInstanceObject(instanceList);
            parseLabelObject(labelList);


            System.out.println("Reading input json file process completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Input file Not Found!");

        }

        return this.getDataset();

    }
    /*
     * This method reads json file from configFilePath instance of config object.
     * Parses JSON then assigns values to config object.
     * @return                          nothing
     */
    public static void readConfig(Config config){

        // JSONParser object created
        JSONParser parser = new JSONParser();

        try {

            // FileReader gets file from given path
            Object jsonParser = parser.parse(new FileReader(config.getConfigFilePath()));
            JSONObject jsonObject = (JSONObject) jsonParser;

            // JSONParser parses with given json keys then assigns userList to JSONArray
            // inputPath and outputPath assigned to config object from JSON file


            JSONArray userList = (JSONArray) jsonObject.get("users");
            JSONArray datasetList =(JSONArray) jsonObject.get("datasets");

            config.setOutputPath((String) jsonObject.get("output path"));
            config.setCurrentDatasetId(Integer.parseInt(jsonObject.get("CurrentDatasetId").toString()));
            config.setConsistencyCheckProbability(Double.parseDouble(jsonObject.get("ConsistencyCheckProbability").toString()));
            // parseUsers method called to parse userList and to add them config object
            parseUsers(userList, config);
            parseDatasets(datasetList,config);
            config.Check();

            System.out.println("Reading config file process completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Config file Not Found!");
        }
    }
    /*
     * Parses sub-array of JSON file then adds instances by-one-by to dataset object
     * @param   instanceList            JSONArray of instanceList from main JSON file
     * @return                          nothing
     */
    protected void parseInstanceObject(JSONArray instanceList) {

        int instanceListSize = instanceList.size();

        for (int i = 0; i < instanceListSize; i++) {

            JSONObject instancesObject = (JSONObject) instanceList.get(i);
            int instanceId = Integer.parseInt(instancesObject.get("id").toString());
            String instanceText = (String) instancesObject.get("instance");
            this.dataset.addInstance(instanceId, instanceText);

        }

    }
    /*
     * Parses sub-array of JSON file then adds labels by-one-by to dataset object
     * @param   labelList               JSONArray of labelList from main JSON file
     * @return                          nothing
     */
    protected void parseLabelObject(JSONArray labelList) {

        int labelListSize = labelList.size();

        for (int i = 0; i < labelListSize; i++) {

            JSONObject instancesObject = (JSONObject) labelList.get(i);
            String labelText = (String) instancesObject.get("label text");
            int labelId = Integer.parseInt(instancesObject.get("label id").toString());
            this.getDataset().addLabel(labelId, labelText);

        }
    }
    /*
     * Parses sub-array of JSON file then adds users by-one-by to config object
     * @param   userList                JSONArray of userList from main JSON file
     * @param   config                  config object
     * @return                          nothing
     */
    protected static void parseUsers(JSONArray userList, Config config) {

        int userListSize = userList.size();

        for (int i = 0; i < userListSize; i++) {

            JSONObject usersObject = (JSONObject) userList.get(i);
            int userId = Integer.parseInt(usersObject.get("user id").toString());
            String userName = (String) usersObject.get("user name");
            String userType = (String) usersObject.get("user type");
            String userPassword = (String) usersObject.get("password");
            config.addUser(userId, userName, userType, userPassword);

        }

    }
    protected static void parseDatasets(JSONArray datasetList, Config config) {

        int datasetListSize = datasetList.size();

        for (int i = 0; i < datasetListSize; i++) {

            JSONObject datasetObject = (JSONObject) datasetList.get(i);
            int datasetId = Integer.parseInt(datasetObject.get("dataset id").toString());
            String datasetName = (String) datasetObject.get("dataset name");

            String datasetPath = (String) datasetObject.get("input path");

            config.addDataset(datasetId, datasetName, 0,datasetPath);

        }

    }
}
