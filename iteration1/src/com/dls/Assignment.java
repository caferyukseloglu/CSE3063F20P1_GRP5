package com.dls;

import java.util.ArrayList;

public class Assignment {
    // @todo assignments have one user only
    // @todo github push
    private int id;
    private Instance instance;
    private User user;
    private Datetime datetime;

    private ArrayList<Label> labels = new ArrayList<Label>(); // Make it limited


    public Assignment(Instance instance, Datetime datetime, User user) {

        setInstance(instance);
        setDatetime(datetime);
        setUser(user);


    }

    protected void setInstance(Instance instance) {
        this.instance = instance;
    }

    protected void setDatetime(Datetime datetime) {
        this.datetime = datetime;
    }

    protected void setUser(User user) {
        this.user = user;
    }

    protected User getUser() {
        return this.user;
    }

    protected Datetime getDatetime() {
        return this.datetime;
    }

    protected void addLabel(Label label) {
        this.labels.add(label);
    }

    protected void addLabelById(int id) {
        Label label = this.instance.getDataset().getLabelById(id);
        addLabel(label);
    }

    protected void addLabelByText(String text) {
        Label label = this.instance.getDataset().getLabelByText(text);
        addLabel(label);
    }

    protected void addLabels(ArrayList<Label> labels) {
        for (Label label : labels) {
            addLabel(label);
        }
    }

    protected void addLabelByIdArray(ArrayList<Integer> ids) {
        for (Integer id : ids) {
            addLabelById(id);
        }
    }

    protected void addLabelByTextArray(ArrayList<String> texts) {
        for (String text : texts) {
            addLabelByText(text);
        }
    }

    protected void getLabels() {
        for (Label label : this.labels) {
            System.out.println(label.getId() + " " + label.getText());
        }
    }

    protected ArrayList<Label> getLabelList() {
        return this.labels;
    }


}
