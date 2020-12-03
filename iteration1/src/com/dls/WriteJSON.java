package com.dls;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJSON {

    private Dataset dataset;
    private String outputFileName;
    public WriteJSON(Dataset dataset){

        setDataset(dataset);
        write();
    }

    private void setDataset(Dataset dataset){ this.dataset = dataset; }

    protected void setOutputFileName(String outputFileName){ this.outputFileName = outputFileName; }

    protected void write() {

        JSONObject mainJSON = new JSONObject();
        mainJSON.put("dataset id", this.dataset.getId());
        mainJSON.put("dataset name", this.dataset.getName());
        mainJSON.put("maximum number of labels per instance", this.dataset.getMaxNumberOfLabels());


        // Sub-arrays
        JSONArray labelsJSON = new JSONArray();
        JSONArray instancesJSON = new JSONArray();
        JSONArray assignmentsJSON = new JSONArray();


        // @todo add some comments
        for (Label label : this.dataset.getLabels()) {
            JSONObject singleLabelJSON = new JSONObject();
            singleLabelJSON.put("label text", label.getText());
            singleLabelJSON.put("label id", label.getId());

            labelsJSON.add(singleLabelJSON);
        }

        //mainJSON.put("class labels", labelsJSON);



        // @todo add some comments
        for (Instance instance : this.dataset.getInstances()){
            JSONObject singleInstanceJSON = new JSONObject();
            singleInstanceJSON.put("id", instance.getId());
            singleInstanceJSON.put("instance", instance.getText());
            instancesJSON.add(singleInstanceJSON);

            for(Assignment assignment : instance.getAssignments()){
                JSONObject singleAssignmentJSON = new JSONObject();
                singleAssignmentJSON.put("datetime", assignment.getDatetime().getRawDatetime());
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

        try {
            FileWriter file = new FileWriter("output.json");
            file.write(mainJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
