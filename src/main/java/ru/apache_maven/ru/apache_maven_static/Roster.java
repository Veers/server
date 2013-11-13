package ru.apache_maven.ru.apache_maven_static;

import ru.apache_maven.Client;

import java.net.InetSocketAddress;
import java.util.*;


public class Roster {
    private static HashMap<String, Client> roster;

    public static void initalize() {
        roster = new HashMap<String, Client>();
    }

    public static void setPerson(String login, Client client) {
        client.setLogin(login);
        if (!roster.containsKey(login)) {
            roster.put(login, client);
        } else {
            int i = 1;
            while (roster.containsKey(login)) {
                login = login + i;
                i++;
            }
            client.setLogin(login);
            roster.put(login, client);
        }
    }

    public static void showAll() {
        Collection<Client> c = roster.values();
        for (Client type : c) System.out.println(type);
    }

    public static InetSocketAddress getAddressByLogin(String name) {
        Client client = roster.get(name);
        assert (client != null);
        return client.getInetSocketAddress();
    }

    public static String[] getUsers() {
        Set<String> strings = roster.keySet();
        return strings.toArray(new String[strings.size()]);
    }

    public static ArrayList<Client> getClientsList() {
        ArrayList<Client> clientsList = new ArrayList<Client>(roster.entrySet().size());

        String s = "";
        for(Map.Entry<String, Client> m : roster.entrySet()){
            clientsList.add(m.getValue());
            System.out.println(m.getKey());
            s = s + m.getKey();
        }
        return clientsList;
    }

    public static void deletePerson(String login) {
        roster.remove(login);
    }
    //todo

}
