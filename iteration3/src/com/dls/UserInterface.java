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
            this.userMenuInterface();
        }else if(answer.equals("3")){
            System.out.println("\u001B[34m"+"Enter instance ID."+"\u001B[0m");
            Scanner instanceIDScanner = new Scanner(System.in);
            String instanceID = instanceIDScanner.nextLine();
            this.config.getActiveDataset().getInstances().get(Integer.parseInt(instanceID)).printPerformanceMetrics();
            this.userMenuInterface();
        }else if(answer.equals("4")){
            this.config.getActiveUser().printPerformanceMetrics();
            this.userMenuInterface();
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
            this.randomBot = new RandomBot(config.getActiveDataset(), config.getActiveUser(),config);
            this.userMenuInterface();
    }





}