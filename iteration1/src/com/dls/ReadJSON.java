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
    private String jsonPath;

    public ReadJSON(Dataset dataset, String jsonPath){

        setDataset(dataset);
        setJsonPath(jsonPath);
        read();
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


}
