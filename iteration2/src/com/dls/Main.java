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

        Config config = new Config("C:\\Users\\ahmet\\IdeaProjects\\CSE3063F20P1_GRP5_iteration1\\CSE3063F20P1_JAVA_GRP5_iteration1\\config.json");
        ReadJSON read = new ReadJSON(config);
        Dataset dataset = read.readInput();
        String key = "exit";
        Scanner myObj;
        do {


            RandomBot randomBot = new RandomBot(dataset, config.getActiveUser());
            config.loginInterface();
            RandomBot randomBot1 = new RandomBot(dataset, config.getActiveUser());

            myObj = new Scanner(System.in);
            System.out.println("Write exit to write file");
        }
        while (! key.equals(myObj.nextLine()));

        WriteJSON write = new WriteJSON(dataset, config);

    }


}

