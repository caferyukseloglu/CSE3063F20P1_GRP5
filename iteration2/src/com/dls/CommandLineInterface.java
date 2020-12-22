package com.dls;

import java.util.Scanner;

public class CommandLineInterface {

    public static void main(){
        Log.setupLogger();
        Config config = new Config("config.json");
        ReadJSON read = new ReadJSON(config);
        Dataset dataset = read.readInput();
        //RandomBot randomBot = new RandomBot(dataset, config.getActiveUser());
        String key = "exit";
        Scanner myObj;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Saving......");
                WriteJSON write = new WriteJSON(dataset, config);
            }
        });

        Instance instance = dataset.getInstances().get(0);
        Assignment assignment = instance.addAssignment(config.getActiveUser());
        assignment.addLabelById(1);
        assignment.addLabelById(2);
        // TEST

        config.logout();
    }



}
