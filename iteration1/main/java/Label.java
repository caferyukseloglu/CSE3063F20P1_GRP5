public class Label {

    private int id;
    private String text;

    public Label(int id, String name){
        setId(id);
        setText(name);
    }

    private void setId(int id){
        this.id = id;
        // @todo Check if it used before
    }

    private void setText(String text){
        this.text = text;
        // @todo Check if this label exist
    }

    protected int getId(){
        return this.id;
    }

    protected String getText(){
        return this.text;
    }

    protected void updateText(String text){
        this.text = text;
    }
}