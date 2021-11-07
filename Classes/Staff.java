package com.example.project;

public class Staff {
    private int id;
    private String username,password;
    private int isAdmin;

    public Staff(){}
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
