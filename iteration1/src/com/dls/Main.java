package com.dls;

public class Main {

    public static void main(String[] args) {

        // Sample Test
        // Read JSON -> Creates Objects -> Runs RandomBot -> Write JSON

        Dataset fd = new Dataset(1, "Multilabel Topic Classification Dataset", 10);
        User user = new User(1, "Test User Name", "Test User Type");
        Datetime datetime = new Datetime("12/12/2020, 04:52:21");
        ReadJSON rj = new ReadJSON(fd, "/Users/eminsafatok/IdeaProjects/DLSProject/CSE3063F20P1_GRP5/iteration1/input_1.json");
        RandomBot rb = new RandomBot(fd, fd.getMaxNumberOfLabels());
        WriteJSON wj = new WriteJSON(fd);

    }


}

