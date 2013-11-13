package ru.apache_maven;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private InetAddress inetAddress;
    private InetSocketAddress inetSocketAddress;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private String login = "";

    public Client() {

    }

    public Client(Socket socket) {
        try {
            new Client(socket, socket.getInetAddress(), new InetSocketAddress(getInetAddress().getHostAddress(), getSocket().getPort()), (ObjectInputStream) getSocket().getInputStream(), (ObjectOutputStream) getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(InetSocketAddress inetSocketAddress) {
        setInetSocketAddress(inetSocketAddress);
        setInetAddress(getInetSocketAddress().getAddress());
        try {
            new Client(new Socket(getInetAddress(), getInetSocketAddress().getPort()), getInetAddress(), inetSocketAddress, (ObjectInputStream) getSocket().getInputStream(), (ObjectOutputStream) getSocket().getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(Socket socket, InetAddress inetAddress, InetSocketAddress inetSocketAddress) {
        try {
            new Client(socket, inetAddress, inetSocketAddress, (ObjectInputStream) getSocket().getInputStream(), (ObjectOutputStream) getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(Socket socket, InetAddress inetAddress, InetSocketAddress inetSocketAddress, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        setSocket(socket);
        setInetAddress(inetAddress);
        setInetSocketAddress(inetSocketAddress);
        setObjectInputStream(objectInputStream);
        setObjectOutputStream(objectOutputStream);
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
