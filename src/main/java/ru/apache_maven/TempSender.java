package ru.apache_maven;

import ru.apache_maven.ru.apache_maven_static.Printer;
import ru.apache_maven.ru.apache_maven_static.Roster;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TempSender {
    private static ObjectOutputStream objectOutputStream = null;

    public static void initialize(ObjectOutputStream oos) {
        setObjectOutputStream(oos);
    }

    public static void sendvia(Message msg) {
        Printer.printLine("Try to send back message...");
        Printer.printLine("-----> SEND TO: " + msg.getToUser());
        Printer.printLine("-----> COMMAND: " + msg.getCommand());
        Printer.printLine("-----> TEXT: " + msg.getText());
        Printer.printLine("-----> ISBROADCAST: " + msg.isBroadCast());
        try {
            getObjectOutputStream().writeObject(msg);
            Printer.printLine("Try to flush...");
            getObjectOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Printer.printLine("Message send back!");

    }

    public static void sendTo(String to, Message msg){
        InetSocketAddress address = Roster.getAddressByLogin(to);
        try {
            Socket socket = new Socket(address.getAddress(), address.getPort());
            ObjectOutputStream oos = (ObjectOutputStream) socket.getOutputStream();
            oos.writeObject(msg);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            Printer.printLine("Error to initialize private socket in SenderClass...");
        }
    }

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        TempSender.objectOutputStream = objectOutputStream;
    }

    public static boolean sendBroadCast(String login, String text){
        Message m = new Message(text);
        m.setLogin(login);
        try{
            objectOutputStream.writeObject(m);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } catch (IOException ie){
            ie.printStackTrace();
            return false;
        }
    }
}
