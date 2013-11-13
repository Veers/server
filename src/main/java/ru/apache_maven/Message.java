package ru.apache_maven;

import java.io.Serializable;

public class Message implements Serializable {
    private String command = "";
    private String text;
    private boolean isBroadCast = false;

    public Message() {

    }

    public Message(String text) {
        this.text = text;
    }

    public Message(String command, String data) {
        this.command = command;
        this.text = data;
    }

    public Message(String text, boolean broadcast) {
        setBroadCast(broadcast);
        this.text = text;
    }

    public void setBroadCastMessage() {
        setBroadCast(true);
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
    //todo
}
