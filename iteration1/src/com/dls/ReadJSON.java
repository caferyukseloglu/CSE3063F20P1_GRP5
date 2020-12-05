package com.dls;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

/**
 * The ReadJSON class is @todo complete comments.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class ReadJSON {

    private Dataset dataset;
    private Config config;

    public ReadJSON(Config config){

        setConfig(config);

    }

    protected void setDataset(Dataset dataset){
        this.dataset = dataset;
    }

    protected void setConfig(Config config){
        this.config = config;
    }

    protected Dataset getDataset(){
        return this.dataset;
    }

    protected Config getConfig(){
        return this.config;
    }

    protected void parseInstanceObject(JSONArray instanceList) {

        int size = instanceList.size();

        for (int i = 0; i < size; i++) {

            JSONObject instancesObject = (JSONObject) instanceList.get(i);

            int instanceId = Integer.parseInt(instancesObject.get("id").toString());
            String instanceText = (String) instancesObject.get("instance");

            this.dataset.addInstance(instanceId, instanceText);

        }

    }

    protected void parseLabelObject(JSONArray LabelsList) {

        int size = LabelsList.size();

        for (int i = 0; i < size; i++) {

            JSONObject instancesObject = (JSONObject) LabelsList.get(i);

            String labelText = (String) instancesObject.get("label text");
            int labelId = Integer.parseInt(instancesObject.get("label id").toString());

            this.getDataset().addLabel(labelId, labelText);

        }
    }

    protected Dataset readInput(){
        JSONParser parser = new JSONParser();

        try {

            Object jsonParser = parser.parse(new FileReader(getConfig().getInputPath()));
            JSONObject jsonObject = (JSONObject) jsonParser;

            JSONArray instanceList = (JSONArray) jsonObject.get("instances");
            JSONArray labelList = (JSONArray) jsonObject.get("class labels");
            int datasetId =Integer.parseInt(jsonObject.get("dataset id").toString());
            String datasetName =(String) jsonObject.get("dataset name");
            int maxNumberOfLabels = Integer.parseInt(jsonObject.get("maximum number of labels per instance").toString());

            Dataset dataset = new Dataset(datasetId, datasetName, maxNumberOfLabels);
            setDataset(dataset);

            parseInstanceObject(instanceList);
            parseLabelObject(labelList);



        } catch (Exception e) {

            e.printStackTrace();

        }

        return this.getDataset();

    }

    public static void readConfig(Config config){

        JSONParser parser = new JSONParser();

        try {

            Object jsonParser = parser.parse(new FileReader(config.getConfigFilePath()));
            JSONObject jsonObject = (JSONObject) jsonParser;


            JSONArray userList = (JSONArray) jsonObject.get("users");
            config.setInputPath((String) jsonObject.get("input path"));
            config.setOutputPath((String) jsonObject.get("output path"));

            parseUsers(userList, config);


        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("ERROR: File Not Found!");
        }


    }

    protected static void parseUsers(JSONArray userList, Config config) {

        int size = userList.size();

        for (int i = 0; i < size; i++) {

            JSONObject usersObject = (JSONObject) userList.get(i);

            int userId = Integer.parseInt(usersObject.get("user id").toString());
            String userName = (String) usersObject.get("user name");
            String userType = (String) usersObject.get("user type");
            String userPassword = (String) usersObject.get("password");

            config.addUser(userId, userName, userType, userPassword);

        }

    }

}
