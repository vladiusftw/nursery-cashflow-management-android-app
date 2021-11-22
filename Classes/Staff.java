package com.example.csit242_project.Classes;

public class Staff {
    private int id;
    private String username,password;
    private int isAdmin;

    public Staff(){}
    public Staff(String username, String password,int isAdmin){
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    public Staff(int id, String username, String password,int isAdmin){
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // getters
    public int getId(){return id;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public int isAdmin(){return isAdmin;}

    public String toString(){
        return "ID: " + id + "\tUsername: " + username + "\tPassword: "
                + password + "\tIsAdmin: " + (isAdmin == 1);
    }
}
