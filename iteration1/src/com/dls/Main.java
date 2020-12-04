package com.dls;

public class Main {

    public static void main(String[] args) {
        test();
    }

    public static void test(){
        // Sample Test
        // Read JSON -> Creates Objects -> Runs RandomBot -> Write JSON

        Dataset fd = new Dataset(1, "Multilabel Topic Classification Dataset", 3);
        User user = new User(1, "Test User Name", "Test User Type");
        Datetime datetime = new Datetime();
        ReadJSON rj = new ReadJSON(fd, "input_1.json");
        fd.printDatasetDetailed();
        RandomBot rb = new RandomBot(fd);
        WriteJSON wj = new WriteJSON(fd);
    }



}

