package ru.apache_maven;

import ru.apache_maven.ChatHistory;
import ru.apache_maven.ru.apache_maven_static.Roster;
import ru.apache_maven.ru.apache_maven_static.Printer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;


public class Connector extends Thread {
    MessageParser msgParser;

    private InetSocketAddress clientInetSocketAddress;

    private SocketAddress clientAddress;

    // final ????
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;

    private int inPackets = 0;
    private int outPackets = 0;

    private Timer timer;

    private int maxTimeout;
    private long lastReadTime;

    private Message message;
    private String login;

    private boolean flag = false;

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
            objectInputStream = new ObjectInputStream(socket.getInputStream());
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
        // todo

        try {
            this.message = (Message) objectInputStream.readObject();

            login = this.message.getLogin();

            //todo parse first message

            //add new user
            Roster.setPerson(login, client);

            //online users
            this.message.setUsers(Roster.getUsers());

            //Broadcast Notification

            //Запускаем таймер
            this.timer = new Timer(Config.DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try { //check for online
                        if (inPackets == outPackets) {
                            outputStream.writeObject(new Ping());
                            outPackets++;
                            System.out.println(outPackets + " out");
                        } else { //disconnect
                            throw new SocketException();
                        }
                    } catch (SocketException ex1) {
                        System.out.println("packages not clash");
                        System.out.println(login + " disconnected!");
                        //delete from online users
                        Roster.deletePerson(login);
                        // todo Notification to Chat
                        flag = true;
                        timer.stop();
                    } catch (IOException ex2) {
                        ex2.printStackTrace();
                    }
                }
            });

            this.timer.start();

            //Start ping
            outputStream.writeObject(new Ping());
            this.outPackets++;
            System.out.println(outPackets + " out");

            //wait for messages
            while (true) {
                //end when ping ended
                if (this.flag) {
                    this.flag = false;
                    break;
                }
                //receive message
                this.message = (Message) objectInputStream.readObject();

                //if message instance of ping
                //todo tru parse must be here
                if (this.message instanceof Ping) {
                    this.inPackets++;
                    System.out.println(this.inPackets + " in");
                } else if (!message.getCommand().equals(Config.HELLO_MESSAGE)) {
                    System.out.println("[" + login + "]: " + message.getText());
                    ChatHistory.addMessage(this.message);
                } else {
                    outputStream.writeObject(ChatHistory.getHistory());
                    //this.broadcast(getUserList().getClientsList(), new Message("Server-Bot", "The user " + login + " has been connect"));
                }

                this.message.setUsers(Roster.getUsers());


                //todo  broadcast notification

                if (!(message instanceof Ping) && !message.getCommand().equals(Config.HELLO_MESSAGE)) {
                    System.out.println("Send broadcast Message: " + message.getCommand() + "\"");
                    //this.broadcast(getUserList().getClientsList(), this.c);
                }
            }

        } catch (SocketException e) {
            System.out.println(login + " disconnected!");
            //todo broadcast notification
            //this.broadcast(getUserList().getClientsList(), new Message("Server-Bot", "The user " + login + " has been disconnect", getUserList().getUsers()));
            this.timer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

