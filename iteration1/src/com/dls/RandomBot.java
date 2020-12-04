package com.dls;
import java.util.Random;

/**
 * The RandomBot class
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class RandomBot {

    private Dataset dataset;
    private int maxNumberOfLabels;
    private User user;
    private Datetime datetime;
    private Random random;

    public RandomBot(Dataset dataset){
        setDataset(dataset);
        setUser(new User(1, "Random Bot v1", "RandomBot"));
        setDatetime(new Datetime());
        setRandom();
        run();



    }

    private void setDataset(Dataset dataset){
        this.dataset = dataset;
        this.maxNumberOfLabels = dataset.getMaxNumberOfLabels();
    }


    protected void setUser(User user){
        this.user = user;
    }

    private void setDatetime(Datetime datetime){
        this.datetime = datetime;
    }

    private void setRandom(){
        this.random = new Random();
    }

    private int getRandomInt(){
        return random.nextInt(this.maxNumberOfLabels) + 1;
    }
    // @todo EXCEPTION: What if maxNumberOfLabels > dataset.labels.size()
    private Label getRandomLabel(){

        return this.dataset.getLabels().get(getRandomInt() - 1);

    }

    protected void run(){

        for(Instance instance : this.dataset.getInstances()){

            Assignment assignment = instance.addAssignment(this.datetime, this.user);

            int numberOfLabelCount = getRandomInt();
            for (int i = 0; i < numberOfLabelCount; i++) {

                Label label = getRandomLabel();
                assignment.addLabel(label);

            }
        }
    }

}
