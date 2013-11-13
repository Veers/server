package ru.apache_maven;

import ru.apache_maven.ru.apache_maven_static.Roster;
import ru.apache_maven.ru.apache_maven_static.Printer;

import java.io.*;
import java.net.*;


public class Connector extends Thread {
    MessageParser msgParser;

    private SocketAddress clientAddress;

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

        Roster.setPerson(socket.getInetAddress(), socket.getInetAddress().toString());
        Printer.printLine("-----> Set name: " + socket.getInetAddress().toString());
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
            Printer.printLine("Client " + Roster.getNameByInetAddress(this.socket.getInetAddress()) + " disconnected");
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

