package com.dls;
import java.util.ArrayList;
import java.util.Scanner;

public class Config {


    private ArrayList<User> users;
    private User activeUser;
    private String inputPath;
    private String outputPath;

    protected void setInputPath(String inputPath){
        this.inputPath = inputPath;
    }

    protected void setOutputPath(String outputPath){
        this.outputPath = outputPath;
    }

    protected void addUser(User user){
        this.users.add(user);
    }

    protected void setActiveUser(User user){
        this.activeUser = user;
    }

    protected ArrayList<User> getUsers(){
        return this.users;
    }

    protected void login(String userName, String password){
        for(User user : getUsers()){
            if(user.checkPassword(userName, password)){
                this.setActiveUser(user);
                System.out.println("LOGIN SUCCESSFUL!");
            }else {
                System.out.println("LOGIN ERROR!");
                loginInterface();
            }
        }
    }

    protected void loginInterface(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter User Name");
        String userName = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();

        login(userName, password);

    }


}

