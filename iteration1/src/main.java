

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Dataset dataset = new Dataset(1, "1", 2);
        Scanner in = new Scanner(System.in);
        JSONParser parser = new JSONParser();
        String s = in.nextLine();
        try {
            Object obj = parser.parse(new FileReader(s));


            JSONObject jsonObject = (JSONObject) obj;


            JSONArray instanceList = (JSONArray) jsonObject.get("instances");
            JSONArray LabelsList = (JSONArray) jsonObject.get("class labels");
            //System.out.println(instanceList);
            //System.out.println(LabelsList);

            parseInstanceObject(instanceList, dataset);
            parseLabelsObject(LabelsList, dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseInstanceObject(JSONArray instanceList, Dataset dataset) {
        int size = instanceList.size();
        for (int i = 0; i < size; i++) {
            JSONObject instancesObject = (JSONObject) instanceList.get(i);

            String instance = (String) instancesObject.get("instance");
            //System.out.println(instance);

            int id = Integer.parseInt(instancesObject.get("id").toString());

            //System.out.println(id);
            dataset.addInstance(id, instance);
        }

    }

    private static void parseLabelsObject(JSONArray LabelsList, Dataset dataset) {
        int size = LabelsList.size();
        for (int i = 0; i < size; i++) {
            JSONObject instancesObject = (JSONObject) LabelsList.get(i);
            String label = (String) instancesObject.get("label text");
            //System.out.println(label);

            int labelId = Integer.parseInt(instancesObject.get("label id").toString());
            ;
            //System.out.println(labelId);
            dataset.addLabel(labelId, label);

        }
        dataset.getLabels();
        User user = new User(1, "Emin Safa Tok", "TESTv1");
        Datetime datetime = new Datetime("TESTv1");
        for (Instance instance : dataset.getInstances()) {

            Assignment assignment = instance.addAssignment(datetime, user);
            assignment.addLabelById(1);
            assignment.addLabelById(2);
        }
        write(dataset);

    }

    private static void write(Dataset dataset) {

        JSONObject object = new JSONObject();
        object.put("dataset id", 1);
        object.put("dataset name", "Sentiment Dataset");
        object.put("maximum number of labels per instance", 1);

        JSONArray label_1 = new JSONArray();
        JSONArray instance_1 = new JSONArray();

        for (Label label : dataset.getLabelList()) {
            JSONObject objectforlabel = new JSONObject();
            objectforlabel.put("label id", label.getId());
            objectforlabel.put("label text", label.getText());
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



