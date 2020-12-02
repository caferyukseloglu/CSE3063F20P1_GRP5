public class User {

    private int id;
    private String name;
    private String type;


    public User(int id, String name, String type){
        setId(id);
        setName(name);
        setType(type);
    }

    private void setId(int id){
        this.id = id;
        // @todo Check if it used before
    }

    private void setName(String name){
        this.name = name;
    }

    private void setType(String type){
        this.type = type;
    }

    protected int getId(){
        return this.id;
    }

    protected String getName(){
        return this.name;
    }

    protected String getType(){
        return this.type;
    }

    protected void updateName(String name){
        this.name = name;
    }

    protected void updateType(String type){
        this.type = type;
    }
}
