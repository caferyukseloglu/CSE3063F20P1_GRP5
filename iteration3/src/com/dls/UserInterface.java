package com.dls;

import java.util.Scanner;

public class UserInterface {

    private Config config;
    private RandomBot randomBot;
    public UserInterface(Config config){

        this.config = config;
        System.out.println("WELCOME TO DATA LABELING SYSTEM");

    }

    public void userMenuInterface(){


        System.out.println("\u001B[34m"+"<-----      M E N U      ----->"+"\u001B[0m");
        System.out.println("\u001B[34m"+"1. LOGIN AND ASSIGN LABEL"+"\u001B[0m");
        System.out.println("\u001B[34m"+"2. SHOW DATASET PERFORMANCE METRICS"+"\u001B[0m");
        System.out.println("\u001B[34m"+"3. SHOW INSTANCE PERFORMANCE METRICS"+"\u001B[0m");
        System.out.println("\u001B[34m"+"4. SHOW USER PERFERMANCE METRICS"+"\u001B[0m");
        System.out.println("\u001B[34m"+"5. LOGOUT AND EXIT"+"\u001B[0m");
        System.out.println("\u001B[34m"+"Enter menu item number."+"\u001B[0m");

        Scanner answerScanner = new Scanner(System.in);
        String answer = answerScanner.nextLine();
        if(answer.equals("1")){
            this.loginAndAssignInterface();
        }else if(answer.equals("2")){
            this.config.getActiveDataset().printPerformanceMetrics();
        }else if(answer.equals("3")){
            System.out.println("\u001B[34m"+"Enter instance ID."+"\u001B[0m");
            Scanner instanceIDScanner = new Scanner(System.in);
            String instanceID = instanceIDScanner.nextLine();
            this.config.getActiveDataset().getInstances().get(Integer.parseInt(instanceID)).printPerformanceMetrics();
        }else if(answer.equals("4")){
            this.config.getActiveUser().printPerformanceMetrics();
        }else if(answer.equals("5")){
            this.config.logout();
            System.out.println("THANK YOU & GOOD BYE!");
        }else{
            System.out.println("WRONG INPUT TRY AGAIN!");
            this.userMenuInterface();
        }

    }

    public void loginAndAssignInterface(){
        config.loginInterface();
        if(config.getActiveUser().getType().equals("MachineLearningBot")){
            this.randomBot = new RandomBot(config.getActiveDataset(), config.getActiveUser(), config);
            this.randomBot.run();
        }else{
            System.out.println("USER TYPE: "+config.getActiveUser().getType());
            this.userAssignmentInterface();
            System.out.println("\u001B[31m"+"COMPLETED."+"\u001B[0m");
        }
        this.userMenuInterface();
    }

    public void userAssignmentInterface(){
        this.randomBot = new RandomBot(config.getActiveDataset(), config.getActiveUser(), config);
        Scanner select;
        for(Instance instance : config.getActiveDataset().getInstances()) {
            if (Math.round(this.config.getActiveUser().getConsistencyCheckProbability()) < this.randomBot.getRandom()) {

                Assignment assignment = instance.addAssignment(this.config.getActiveUser());

                System.out.println("Instance is: " + instance.getText());
                if (config.getActiveDataset().getMaxNumberOfLabels() > 1) {
                    System.out.println("You can select more than one label for this instance. Example = label id1,label id2");
                    for (Label label : this.config.getActiveDataset().getLabels()) {
                        System.out.println(label.getId() + " " + label.getText());
                    }

                    select = new Scanner(System.in);
                    String input = select.nextLine();
                    String[] strs = input.split(",");
                    for (String str : strs) {
                        Label label = this.randomBot.getLabel(Integer.parseInt(str));
                        assignment.addLabel(label);
                        WriteJSON write = new WriteJSON(config.getActiveDataset(), config, instance);
                    }
                } else {
                    System.out.println("You can select Only ONE label for this instance. Example = label id1");
                    for (Label label : this.config.getActiveDataset().getLabels()) {
                        System.out.println(label.getId() + " " + label.getText());
                    }
                    select = new Scanner(System.in);
                    int input = select.nextInt();
                    Label label = this.randomBot.getLabel(input);
                    assignment.addLabel(label);
                    WriteJSON write = new WriteJSON(this.config.getActiveDataset(), config, instance);
                }
            } else {
                for (Assignment assignment: instance.getAssignments()){
                    assignment.getInstance().addAssignment(this.config.getActiveUser());
                    if (this.config.getActiveDataset().getMaxNumberOfLabels() > 1) {
                        System.out.println("You can select more than one label for this instance. Example = label id1,label id2");
                        for (Label label : this.config.getActiveDataset().getLabels()) {
                            System.out.println(label.getId() + " " + label.getText());
                        }

                        select = new Scanner(System.in);
                        String input = select.nextLine();
                        String[] strs = input.split(",");
                        for (String str : strs) {
                            Label label = this.randomBot.getLabel(Integer.parseInt(str));
                            assignment.addLabel(label);
                            WriteJSON write = new WriteJSON(this.config.getActiveDataset(), config, instance);
                        }
                    } else {
                        System.out.println("You can select Only ONE label for this instance. Example = label id1");
                        for (Label label : this.config.getActiveDataset().getLabels()) {
                            System.out.println(label.getId() + " " + label.getText());
                        }
                        select = new Scanner(System.in);
                        int input = select.nextInt();
                        Label label = this.randomBot.getLabel(input);
                        assignment.addLabel(label);
                        WriteJSON write = new WriteJSON(config.getActiveDataset(), config, instance);
                    }


                }

            }

        }
        System.out.println("You have completed all instances.");
        System.out.println("\u001B[31m"+"Would you like to assign one more time?"+"\u001B[0m");
        Scanner answerScanner = new Scanner(System.in);
        System.out.println("y (yes) / n (no)");
        String answer = answerScanner.nextLine();
        if(answer.equals("y")){
            this.userAssignmentInterface();
        }
    }


}
