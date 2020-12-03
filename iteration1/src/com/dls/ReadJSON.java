import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Scanner;

public class ReadJSON {


    public void readJson(Dataset dataset) {
        //Dataset dataset = new Dataset(1, "1", 2);
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
//        User user = new User(1, "Emin Safa Tok", "TESTv1");
//        Datetime datetime = new Datetime("TESTv1");
//        for (Instance instance : dataset.getInstances()) {
//
//            Assignment assignment = instance.addAssignment(datetime, user);
//            assignment.addLabelById(1);
//            assignment.addLabelById(2);
//        }


    }
}
