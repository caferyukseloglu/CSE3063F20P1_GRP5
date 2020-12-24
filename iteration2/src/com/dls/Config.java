package com.dls;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Config class stores configuration information of program.
 * @version iteration-2.0
 * @since 2020-12-01
 *
 */
public class Config {

    private Dataset activeDataset;
    private User activeUser;
    private String configFilePath;
    private Double consistencyCheckProbability;
    private Integer currentDatasetId;
    private String outputPath;
    private ArrayList<Dataset> datasets = new ArrayList<Dataset>();
    private ArrayList<User> users = new ArrayList<User>();
    private final static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );

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
     * Sets active user object as instance variable
     * @param   user                    user object to set
     * @return                          nothing
     */
    protected void setActiveUser(User user){
        System.out.println("Active is:"+user.getName());
        this.activeUser = user;
    }
    /*
     * Sets dataset object as instance variable
     * @param   dataset                 dataset object to set
     * @return                          nothing
     */
    protected void setActiveDataset(Dataset dataset){
        System.out.println("Active dataset:"+dataset.getName());
        this.activeDataset = dataset;

    }
    /*
     * Sets consistency check probability as instance variable
     * @param   consistencyCheckProbability     consistency check probability
     * @return                                  nothing
     */
    protected void setConsistencyCheckProbability(Double consistencyCheckProbability){
        this.consistencyCheckProbability = consistencyCheckProbability * 100.0;
    }
    /*
     * Sets current dataset ID as instance variable
     * @param   datasetId               dataset id to set
     * @return                          nothing
     */
    protected void setCurrentDatasetId(Integer datasetId){
        System.out.println("Current dataset is:"+datasetId);
        this.currentDatasetId = datasetId;
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
     * Gets ArrayList of datasets
     * @return                          array list of datasets
     */
    protected ArrayList<Dataset> getDatasets(){
        return this.datasets;
    }
    /*
     * Gets logged in user object
     * @return                          user object
     */
    protected User getActiveUser(){
        return this.activeUser;
    }
    /*
     * Gets active dataset object
     * @return                          active dataset object
     */
    protected Dataset getActiveDataset(){
        return this.activeDataset;
    }
    /*
     * Gets current dataset ID
     * @return                          current dataset ID
     * @todo this methos and currentDatasetId parameter should not be used.
     */
    protected Integer getCurrentDatasetId() {
        return this.currentDatasetId;
    }
    /*
     * Gets output path
     * @return                          outputPath
     */
    public String getOutputPath(){
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
        user.setConsistencyCheckProbability(this.consistencyCheckProbability);
        this.users.add(user);
        return user;
    }
    /*
     * Creates an dataset object and adds it to datasets list then returns created dataset object
     * @param   datasetId               dataset id
     * @param   datasetName             dataset name
     * @param   maxNumberOfLabels       maximum number of labels to assign single instance
     * @param   inputPath               input path
     * @return                          created dataset object
     */
    protected Dataset addDataset(Integer datasetId, String datasetName, Integer maxNumberOfLabels, String inputPath){
        Dataset dataset = new Dataset(datasetId, datasetName, maxNumberOfLabels,inputPath);
        this.datasets.add(dataset);
        return dataset;
    }
    /*
     * Assign active dataset
     * @return                          nothing
     */
    protected void  Check(){
        for (Dataset dataset : getDatasets()){
            if(dataset.getId() == getCurrentDatasetId()){
                this.setActiveDataset(dataset);
            }
        }
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
                user.setLoginDatetime();
                loggedIn = true;
                break;
            }
        }
        if(loggedIn){
            System.out.println("LOGIN SUCCESSFUL!");
            logger.log(Level.WARNING,String.valueOf(userName+" is login"));

        }else{
            System.out.println("LOGIN ERROR!");
            logger.log(Level.WARNING,"LOGIN ERROR");
            this.loginInterface();
        }
    }
    /*
     * User logout function, makes activeUser parameter null and calls setLogoutDatetime method of user object.
     * @return                          nothing
     */
    protected User logout(){
        System.out.println("User logged out:"+this.activeUser.getName());
        User user = this.activeUser;
        user.setLogoutDatetime();
        this.activeUser = null;
        return user;
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

