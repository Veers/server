package ru.apache_maven;

import ru.apache_maven.ru.apache_maven_static.Roster;
import ru.apache_maven.ru.apache_maven_static.Printer;
import ru.apache_maven.ru.client.CoreClient;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicReference;

public class App {
    private static Roster list = new Roster();
    private static ChatHistory chatHistory = new ChatHistory();

    public App() {
        Printer.printLine("Welcome to Server side");

        Printer.printLine("Initialize Roster...");
        Roster.initalize();

        ServerSocket servers = null;
        AtomicReference<Socket> fromclient = new AtomicReference<Socket>();

        // create server socket
        try {
            Printer.printLine("Initialize Socket...");
            servers = new ServerSocket(Config.PORT);
        } catch (IOException e) {
            Printer.printLine("Couldn't listen to port 4444");
            System.exit(-1);
        }

        try {
            Printer.printLine("Initialize Listener...");
            Printer.printLine("Waiting for a client...");
            fromclient.set(servers.accept());
            new Connector(fromclient.get());
        } catch (IOException e) {
            Printer.printLine("Can't accept");
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws IOException {
        // server
        new App();
        // client
        //new CoreClient();
    }

    public synchronized static Roster getUserList() {
        return list;
    }

    public synchronized static ChatHistory getChatHistory() {
        return chatHistory;
    }
}