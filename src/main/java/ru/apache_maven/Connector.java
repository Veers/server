package ru.apache_maven;

import ru.apache_maven.ru.apache_maven_static.Roster;
import ru.apache_maven.ru.apache_maven_static.Printer;

import java.io.*;
import java.net.*;


public class Connector extends Thread {
    MessageParser msgParser;

    private InetSocketAddress clientInetSocketAddress;

    private SocketAddress clientAddress;

    // final ????
    private ObjectInputStream ois;
    private ObjectOutputStream outputStream;
    private Socket socket;

    private int maxTimeout;
    private long lastReadTime;

    public Connector(Socket socket) {
        this.lastReadTime = 0;
        int timeout = 10000;
        maxTimeout = 25000;
        this.socket = socket;
        try {
            this.socket.setSoTimeout(timeout);
        } catch (SocketException e) {
            Printer.printLine("Unable to set timeout: " + e.getMessage());
        }
        Printer.printLine("Client connected");
        this.clientAddress = socket.getRemoteSocketAddress();
        this.clientInetSocketAddress = new InetSocketAddress(socket.getInetAddress(), socket.getPort());

        this.msgParser = new MessageParser(socket.getInetAddress());

        try {
            Printer.printLine("Initialize Input Stream...");
            ois = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Printer.printLine("Initialize Output Stream...");
        TempSender.initialize(this.outputStream);
        this.start();

    }

    public void run() {
        Client client = new Client(this.socket);
        Roster.setPerson("user_" + String.valueOf(socket.getInetAddress()), client);
        Printer.printLine("-----> Set name: " + socket.getInetAddress().toString());
        // todo
        try {
            Message msg;
            try {
                while (ois.read() != 0) {
                    msg = (Message) ois.readObject();
                    lastReadTime = System.currentTimeMillis();
                    this.msgParser.parse(msg);
                    Roster.showAll();
                }
            } catch (SocketTimeoutException ste) {
                if (!isConnectionAlive()) {
                    Printer.printLine("Connection terminated...");
                    this.socket.close();
                    Printer.printLine("Restore connection...");
                    setupSocket();
                } else
                    sendPingPacket();
            } catch (EOFException e) {
                e.printStackTrace();
            }
        } catch (SocketException se) {
            Printer.printLine(se.getMessage());
            Printer.printLine("Client " + client.getLogin() + " disconnected");
            Printer.printLine("EXIT...");
        } catch (IOException e) {
            e.printStackTrace();
            Printer.printLine("EXIT...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Printer.printLine("EXIT...");
        }

    }

    private void sendPingPacket() {

    }

    private void setupSocket() {
        try {
            this.socket.connect(this.clientAddress);
        } catch (IOException e) {
            Printer.printLine("Cannot connect to: " + this.clientAddress);
            e.printStackTrace();
        }
    }

    public boolean isConnectionAlive() {
        return System.currentTimeMillis() - lastReadTime < maxTimeout;
    }
}

