import java.util.ArrayList;

public class Dataset {

    private int id;
    private String name;
    private int maxNumberOfLabels;

    protected ArrayList<Label> labels = new ArrayList<Label>(); // Make it limited
    protected ArrayList<User> users = new ArrayList<User>();
    protected ArrayList<Instance> instances = new ArrayList<Instance>();

    public Dataset(int id, String name, int maxNumberOfLabels) {

        // Initialization of Object
         setId(id);
         setName(name);
         setMaxNumberOfLabels(maxNumberOfLabels);

    }

    private void setId(int id){
        this.id = id;
        // @todo Check if it used before
    }

    private void setName(String name){
        this.name = name;
    }

    private void setMaxNumberOfLabels(int maxNumberOfLabels){
        this.maxNumberOfLabels = maxNumberOfLabels;
    }

    protected Instance addInstance(int id, String name){

        Instance instance = new Instance(id, name, this);
        this.instances.add(instance);
        return instance;
    }

    protected void addLabel(int id, String text){
        Label label = new Label(id, text);
        this.labels.add(label);
    }

    protected int getIdOfInstances(Instance instance){

        return this.instances.indexOf(instance);

    }

    protected ArrayList<Instance> getInstances(){

        return this.instances;

    }

    protected Label getLabelByText(String text){
        for(Label label : this.labels){
            if(label.getText().equals(text)){
                return label;
            }
        }
        return new Label(0, text);
    }

    protected Label getLabelById(int id){
        for(Label label : this.labels){
            if(label.getId() == id){
                return label;
            }
        }
        return new Label(id, "");
    }

    protected void getLabels(){
        for(Label label : this.labels){
            System.out.println("ID: "+label.getId() + " Text:" + label.getText());
        }
    }

    protected ArrayList<Label> getLabelList(){
        return this.labels;
    }

    protected ArrayList<Instance> getInstanceList(){
        return this.instances;
    }


}
