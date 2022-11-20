package com.example.rent_home.Models;

public class Users {
    String userName,mail,password,profilepic,lastMessage;

    public Users(String profilepic, String userName, String mail, String password, String lastmessage)
    {
        this.profilepic=profilepic;
        this.mail=mail;
        this.password=password;
        this.lastMessage=lastmessage;
        this.userName=userName;
    }
    public Users(){}

    public Users(String userName,String mail,String password){
        this.userName=userName;
        this.password=password;
        this.mail=mail;
    }
    public String getProfilepic(){
        return profilepic;
    }
    public void setProfilepic(String profilepic)
    {
        this.profilepic=profilepic;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName=userName;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    public String getLastMessage(){
        return lastMessage;
    }
    public void setLastMessage(String lastMessage)
    {
        this.lastMessage=lastMessage;
    }
    public String getMail(){
        return mail;
    }
    public void setMail(String lastMessage)
    {
        this.mail=mail;
    }
    public String getnull(){
        return null;
    }

}

