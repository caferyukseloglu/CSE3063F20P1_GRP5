package com.dls;

import java.util.ArrayList;
public class Instance {

    private int id;
    private String text;
    private boolean status = false; // @todo assigned or not?
    private Dataset dataset;

    protected ArrayList<Assignment> assignments = new ArrayList<Assignment>(); // Make it limited

    public Instance(int id, String text, Dataset dataset){
        setId(id);
        setText(text);
        setDataset(dataset);

    }

    private void setId(int id){
        this.id = id;
        // @todo Check if it used before
    }

    private void setText(String text){
        this.text = text;
    }

    private void setDataset(Dataset dataset){

        this.dataset = dataset;
    }

    protected int getId(){
        return this.id;
    }

    protected String getText(){
        return this.text;
    }

    protected Dataset getDataset(){
        return this.dataset;
    }

    protected void updateText(String text){
        this.text = text;
    }

    protected Assignment addAssignment(Datetime datetime, User user){

        Assignment assignment = new Assignment(this, datetime, user);
        return assignment;
    }

}
