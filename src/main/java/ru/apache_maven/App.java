package ru.apache_maven;

import ru.apache_maven.ru.apache_maven_static.Roster;
import ru.apache_maven.ru.apache_maven_static.Printer;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicReference;

public class App {

    public App() {
        Printer.printLine("Welcome to Server side");

        Printer.printLine("Initialize Roster...");
        Roster.initalize();

        ServerSocket servers = null;
        AtomicReference<Socket> fromclient = new AtomicReference<Socket>();

        // create server socket
        try {
            Printer.printLine("Initialize Socket...");
            servers = new ServerSocket(4444);
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
        new App();
    }
}