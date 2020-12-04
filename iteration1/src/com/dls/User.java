package com.dls;

/**
 * The User class stores variables and methods for each user that stated initially.
 * @version iteration-1.0
 * @since 2020-12-01
 *
 */
public class User {

    private int id;
    private String name;
    private String type;
    private String password;


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

    private void setPassword(String password){
        this.password = password;
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

    protected String getPassword(){
        return this.password;
    }

    protected Boolean checkPassword(String userName, String password){

        return this.getName().equals(userName) && this.getPassword().equals(password);
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
