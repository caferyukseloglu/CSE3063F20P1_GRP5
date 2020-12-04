package com.dls;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

/**
 * The ReadJSON class is @todo complete comments
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class ReadJSON {

    private Dataset dataset;
    private String jsonPath;

    public ReadJSON(String jsonPath){

        //setDataset(dataset);
        setJsonPath(jsonPath);
        //read();
        readConfig();
    }

    protected void read() {

        // @todo Do not need to use Scanner function, so we can deprecate import function and methods.
        // Dataset dataset = new Dataset(1, "1", 2);
        // Scanner in = new Scanner(System.in);


        JSONParser parser = new JSONParser();

        try {

            Object jsonParser = parser.parse(new FileReader(jsonPath));
            JSONObject jsonObject = (JSONObject) jsonParser;

            JSONArray instanceList = (JSONArray) jsonObject.get("instances");
            JSONArray labelList = (JSONArray) jsonObject.get("class labels");

            parseInstanceObject(instanceList);
            parseLabelObject(labelList);


        } catch (Exception e) {

            e.printStackTrace();

        }
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

            this.dataset.addLabel(labelId, labelText);

        }
    }


    protected void setDataset(Dataset dataset){

        this.dataset = dataset;

    }

    protected void setJsonPath(String jsonPath){

        this.jsonPath = jsonPath;

    }

    protected void readInput(){
        JSONParser parser = new JSONParser();

        try {

            Object jsonParser = parser.parse(new FileReader(jsonPath));
            JSONObject jsonObject = (JSONObject) jsonParser;

            JSONArray instanceList = (JSONArray) jsonObject.get("instances");
            JSONArray labelList = (JSONArray) jsonObject.get("class labels");
            int datasetId =Integer.parseInt(jsonObject.get("dataset id").toString());
            String datasetName =(String) jsonObject.get("dataset name");
            int maxnumberoflabels =Integer.parseInt(jsonObject.get("maximum number of labels per instance").toString());
            Dataset dataset = new Dataset(datasetId, datasetName, maxnumberoflabels);
            setDataset(dataset);
            parseInstanceObject(instanceList);
            parseLabelObject(labelList);
            WriteJSON wj = new WriteJSON(dataset);


        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    protected void readConfig(){
        JSONParser parser = new JSONParser();
        try {
            Object jsonParser = parser.parse(new FileReader(jsonPath));
            JSONObject jsonObject = (JSONObject) jsonParser;

            JSONArray userList = (JSONArray) jsonObject.get("users");
            String inputPath = (String) jsonObject.get("input path");
            String outPath = (String) jsonObject.get("output path");
            System.out.println(inputPath);
            setJsonPath(inputPath);
            readInput();
            parseUsers(userList);


        } catch (Exception e) {

            e.printStackTrace();

        }


    }
    protected void parseUsers(JSONArray userList) {

        int size = userList.size();

        for (int i = 0; i < size; i++) {

            JSONObject usersObject = (JSONObject) userList.get(i);

            int UserId = Integer.parseInt(usersObject.get("user id").toString());
            String userName = (String) usersObject.get("user name");
            String userType = (String) usersObject.get("user type");
            String passwords = (String) usersObject.get("user name");
            System.out.println(userName);
            // dataset add user
            //this.dataset.addInstance(instanceId, instanceText);

        }

    }

}
