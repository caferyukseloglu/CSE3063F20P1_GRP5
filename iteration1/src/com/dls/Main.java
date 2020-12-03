package com.dls;

public class Main {

    //static Dataset dataset = new Dataset(1996, "eminsafa");



    public static void main(String[] args) {

        Dataset fd = new Dataset(1, "Multilabel Topic Classification Dataset", 10);

        // OUTPUT:
        User user = new User(1, "Test User Name", "Test User Type");
        Datetime datetime = new Datetime("12/12/2020, 04:52:21");

        ReadJSON rj = new ReadJSON(fd, "/Users/eminsafatok/IdeaProjects/DLSProject/CSE3063F20P1_GRP5/iteration1/input_1.json");


        for (Instance instance : fd.getInstances()){

            Assignment assignment = instance.addAssignment(datetime, user);
            assignment.addLabelByText("Negative");
            assignment.addLabelByText("Positive");

        }
        //fd.printLabels();
        //fd.printInstances();


        //fd.printDatasetDetailed();

        WriteJSON wj = new WriteJSON(fd);





    }


}

