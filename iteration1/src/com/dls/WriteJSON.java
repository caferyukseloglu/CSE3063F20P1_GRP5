import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJSON {

    protected void write(Dataset dataset) {

        JSONObject object = new JSONObject();
        object.put("dataset id", 1);
        object.put("dataset name", "Sentiment Dataset");
        object.put("maximum number of labels per instance", 1);

        JSONArray label_1 = new JSONArray();
        JSONArray instance_1 = new JSONArray();

        for (Label label : dataset.getLabelList()) {
            JSONObject objectforlabel = new JSONObject();
            objectforlabel.put("label text", label.getText());
            objectforlabel.put("label id", label.getId());

            label_1.add(objectforlabel);
        }
        object.put("class labels", label_1);

        for (Instance instance : dataset.getInstances()){
            JSONObject objectforinstance = new JSONObject();
            objectforinstance.put("id", instance.getId());
            objectforinstance.put("instance", instance.getText());
            instance_1.add(objectforinstance);
        }
        object.put("instances", instance_1);

        // user label assign methodu gelince düzenlencek
        JSONObject objectforclassignment = new JSONObject();
        objectforclassignment.put("instance id", 1);
        objectforclassignment.put("class label ids", 1);
        objectforclassignment.put("user1 id", 1);
        objectforclassignment.put("datetime", "12-12-2020,04:49:31");

        JSONObject objectforusers = new JSONObject();
        objectforusers.put("user1 id", 1);
        objectforusers.put("user1 name", "RandomLabelingMechanism");







        JSONArray assignment = new JSONArray();
        assignment.add(objectforclassignment);
        object.put("class label assignments", assignment);

        JSONArray user = new JSONArray();
        user.add(objectforusers);
        object.put("users", user);





        try {
            FileWriter file = new FileWriter("output.json");
            file.write(object.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
