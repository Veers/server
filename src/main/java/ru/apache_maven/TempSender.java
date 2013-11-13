package ru.apache_maven;

import ru.apache_maven.ru.apache_maven_static.Printer;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class TempSender {
    private static ObjectOutputStream objectOutputStream = null;

    public static void initialize(ObjectOutputStream oos) {
        setObjectOutputStream(oos);
    }

    public static void sendvia(Message msg) {
        Printer.printLine("Try to send back message...");
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

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        TempSender.objectOutputStream = objectOutputStream;
    }
}
