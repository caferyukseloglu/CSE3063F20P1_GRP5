package com.dls;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Config class stores configuration information of program.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class Config {

    private ArrayList<User> users = new ArrayList<User>();
    private User activeUser;
    private String inputPath;
    private String outputPath;
    private String configFilePath;

    /*
     * Construct method of the Config class
     * @param   configFilePath          config json file path to read from
     * @return                          nothing
     */
    public Config(String configFilePath){
        setConfigFilePath(configFilePath);
        readConfigFile();
        loginInterface();
    }
    /*
     * Sets user object as instance variable
     * @param   user                    user object to set
     * @return                          nothing
     */
    protected void setActiveUser(User user){
        this.activeUser = user;
    }
    /*
     * Sets input path as instance variable
     * @param   inputPath               input path as string to set
     * @return                          nothing
     */
    protected void setInputPath(String inputPath){
        this.inputPath = inputPath;
    }
    /*
     * Sets output path as instance variable
     * @param   outputPath              output path as string to set
     * @return                          nothing
     */
    protected void setOutputPath(String outputPath){
        this.outputPath = outputPath;
    }
    /*
     * Sets config file path as instance variable
     * @param   configFilePath          config file path as string to set
     * @return                          nothing
     */
    protected void setConfigFilePath(String configFilePath){
        this.configFilePath = configFilePath;
    }
    /*
     * Gets ArrayList of users
     * @return                          array list of users
     */
    protected ArrayList<User> getUsers(){
        return this.users;
    }
    /*
     * Gets logged in user object
     * @return                          user object
     */
    protected User getActiveUser(){
        return this.activeUser;
    }
    /*
     * Gets input path as text
     * @return                          input path as text
     */
    protected String getInputPath(){
        return this.inputPath;
    }
    /*
     * Gets output path as text
     * @return                          output path as String
     */
    protected String getOutputPath(){
        return this.outputPath;
    }
    /*
     * Gets config file path as text
     * @return                          config file path as String
     */
    protected String getConfigFilePath(){
        return this.configFilePath;
    }
    /*
     * Creates an user object and adds it to users list then returns created user object
     * @param   userId                  user id
     * @param   userName                user name
     * @param   userType                user type
     * @param   userPassword            user password
     * @return                          created user object
     */
    protected User addUser(int userId, String userName, String userType, String userPassword){
        User user = new User(userId, userName, userType, userPassword);
        this.users.add(user);
        return user;
    }
    /*
     * If given parameters are correct, sets active user with given credentials
     * Else it calls userInterface method again
     * @param   userName                user name input
     * @param   password                user password input
     * @return                          nothing
     */
    protected void login(String userName, String password){
        boolean loggedIn = false;
        for(User user : getUsers()){
            if(user.checkPassword(userName, password)){
                this.setActiveUser(user);
                loggedIn = true;
                break;
            }
        }
        if(loggedIn){
            System.out.println("LOGIN SUCCESSFUL!");
        }else{
            System.out.println("LOGIN ERROR!");
            this.loginInterface();
        }
    }
    /*
     * Prints login interface and calls login method with given credentials
     * @return                          nothing
     */
    protected void loginInterface(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter User Name");
        String userName = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();

        this.login(userName, password);

    }
    /*
     * Calls readConfig method of ReadJSON class to read config.json file
     * @return                          nothing
     */
    protected void readConfigFile(){
        ReadJSON.readConfig(this);
    }

}

