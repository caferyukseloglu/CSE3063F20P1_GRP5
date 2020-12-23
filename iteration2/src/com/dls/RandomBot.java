package com.dls;
import java.util.Random;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
/**
 * The RandomBot class simulates a labeling mechanism like Machine Learning Algorithms
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class RandomBot {
    private final static Logger logger = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
    private Dataset dataset;
    private int maxNumberOfLabels;
    private User user;
    private Datetime datetime;
    private Random random;
    private Config config;

    /*
     * Construct method of the RandomBot class
     * @param   dataset                 dataset object to run this bot
     * @param   user                    user object
     * @return                          nothing
     */
    public RandomBot(Dataset dataset, User user, Config config){

        setDataset(dataset);
        setUser(user);
        setConfig(config);
        setDatetime(new Datetime());
        setRandom();
        run();

    }
    /*
     * Sets object of parent dataset as instance variable and gets maxNumberOfLabels
     * @param   dataset                 parent dataset of instance
     * @return                          nothing
     */
    private void setDataset(Dataset dataset){
        this.dataset = dataset;
        this.maxNumberOfLabels = dataset.getMaxNumberOfLabels();
    }
    /*
     * Sets user object as instance variable
     * @param   user                    parent dataset of instance
     * @return                          nothing
     */
    protected void setConfig(Config config){this.config = config;}
    protected void setUser(User user){
        this.user = user;
    }
    /*
     * Sets datetime object as instance variable
     * @param   datetime                parent dataset of instance
     * @return                          nothing
     */
    private void setDatetime(Datetime datetime){
        this.datetime = datetime;
    }
    /*
     * Sets random object as instance variable
     * @return                          nothing
     */
    private void setRandom(){
        this.random = new Random();
    }
    /*
     * Gets a random number from 1 to maxNumberOfLabels
     * @return                          random number
     */
    private int getRandomInt(){
        return random.nextInt(this.maxNumberOfLabels) + 1;
    }
    /*
     * Gets a random label with called random number.
     * Labels array list accepts index of the list. Thus, id of label ignored.
     * @todo EXCEPTION: What if maxNumberOfLabels > dataset.labels.size()
     * @return                          label object
     */
    private int getRandom(){return random.nextInt(100);}
    private Label getRandomLabel(){

        return this.dataset.getLabels().get(getRandomInt() - 1);

    }
    /*
     * This method calls all instances of the dataset and assigns random labels.
     * @return                          nothing
     */
    protected void run() {

        for (Instance instance : this.dataset.getInstances()) {

            Assignment assignment = instance.addAssignment(this.user);
            if (!instance.getAssignments().isEmpty()) {
                logger.info(String.valueOf("user: " + this.user.getName() + " labeled this instance ıd: " + instance.getId()));
                try {
                    TimeUnit.MILLISECONDS.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                int numberOfLabelCount = getRandomInt();
                for (int i = 0; i < numberOfLabelCount; i++) {

                    Label label = getRandomLabel();

                    assignment.addLabel(label);
                    logger.info("added random label to this instance" +instance.getId());
                    WriteJSON write = new WriteJSON(dataset, config,instance);
                    logger.info("Writing process completed successfully");
                }
            } else {
                if (Math.round(user.getConsistencyCheckProbability()) > getRandom()) {
                    assignment.addLabel(instance.getFinalLabel());
                    logger.info("added same label to this instance" +instance.getId());

                }

            }
        }
    }
}





