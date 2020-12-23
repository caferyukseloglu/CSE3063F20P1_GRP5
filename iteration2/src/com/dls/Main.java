package com.dls;

import java.util.Scanner;

/**
 * The Data Labeling System (DLS) provides simple, stable and reliable labeling software as a third party integration.
 * This is term project of Marmara University CSE3063 Object-Oriented Software Design Lecture.
 * Group # 5
 *
 * USAGE PROCEDURES
 * 1- Create Config object with config JSON file path.
 * 2- Create ReadJSON object to import instances and other data from JSON file, then run readInput method.
 * 3- Login with config.loginInterface method.
 * 4- [OPTIONAL] Create RandomBot to run random labeling mechanism.
 * 5- [OPTIONAL] It is possible to use dataset object to use DLS with API
 * 5- Create WriteJSON object to export final dataset.
 *
 * This program allows multi-user access simultaneously, main method shows how randomBot and randomBot2 run together.
 *
 * NOTE: Check config.json to configure this software to work with your input file and to see other settings.
 *
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Main {

    public static void main(String[] args) {
        Log.setupLogger();
        Config config = new Config("config.json");
        ReadJSON read = new ReadJSON(config);
        Dataset dataset = read.readInput();
        String key = "exit";
        Scanner myObj;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Saving......");
                //WriteJSON write = new WriteJSON(dataset, config);
            }
        });
        do {

            config.loginInterface();
            RandomBot randomBot2 = new RandomBot(dataset, config.getActiveUser(),config);

            config.logout();
//            Instance instance = dataset.getInstances().get(0);
//            Assignment assignment = instance.addAssignment(config.getActiveUser());
//            assignment.addLabelById(1);
//            assignment.addLabelById(2);
//
//            Assignment assignmentiki = instance.addAssignment(config.getActiveUser());
//            assignmentiki.addLabelById(1);
//            assignmentiki.addLabelById(2);
//
//
//            Instance instance1 = dataset.getInstances().get(1);
//            Assignment assignmentx2 = instance1.addAssignment(config.getActiveUser());
//            assignmentx2.addLabelById(8);
//            assignmentx2.addLabelById(8);
//
//            Assignment assignmentikix2 = instance1.addAssignment(config.getActiveUser());
//            assignmentikix2.addLabelById(1);
//            assignmentikix2.addLabelById(2);
//
//            config.getActiveUser().printPerformanceMetrics();
//            instance.printPerformanceMetrics();
//            dataset.printPerformanceMetrics();
//
//            Scanner scanner = new Scanner(System.in);
//            //System.out.println();
//            dataset.getInstances().get(0).getNumberOfUniqueAssignments();
//            String delay = scanner.nextLine();
            myObj = new Scanner(System.in);

        }
        while (! key.equals(myObj.nextLine()));


    }


}

