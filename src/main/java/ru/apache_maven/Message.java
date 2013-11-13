package ru.apache_maven;

import java.io.Serializable;

public class Message implements Serializable {
    private String login = "";
    private String command = "";
    private String text;
    private String toUser = "";
    private boolean isBroadCast = true;

    private String[] users;

    public Message() {

    }

    public Message(String text) {
        setText(text);
    }

    public Message(String command, String text) {
        setCommand(command);
        setText(text);
    }

    public Message(String text, boolean broadcast) {
        setBroadCast(broadcast);
        setText(text);
    }

    public Message(String toUser, String command, String text){
        setToUser(toUser);
        setCommand(command);
        setText(text);
    }

    public void setBroadCastMessage() {
        setBroadCast(true);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setCommand(String str) {
        this.command = str;
    }

    public String getCommand() {
        return this.command;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getText() {
        return this.text;
    }

    public void compose() {

    }

    public boolean isBroadCast() {
        return isBroadCast;
    }

    public void setBroadCast(boolean broadCast) {
        isBroadCast = broadCast;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    //todo
}
