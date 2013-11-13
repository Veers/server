package ru.apache_maven.ru.apache_maven_static;

import ru.apache_maven.CommonEnum;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;


public class Roster {
    private static HashMap<InetSocketAddress, String> roster;

    public static void initalize() {
        roster = new HashMap<InetSocketAddress, String>();
    }

    public static void setPerson(InetSocketAddress address, String name) {
        roster.put(address, name);
    }

    public static void showAll() {
        Collection<String> c = roster.values();
        for (String type : c) System.out.println(type);

    }

    public static void setName(InetSocketAddress key, String name) {
        roster.put(key, name);
    }

    public static String getNameByInetAddress(InetSocketAddress ia) {
        String name = roster.get(ia);
        if (name == null)
            name = String.valueOf(CommonEnum.UNKNOWN_NAME);
        return name;
    }

    public static boolean getUser(String name){
        return roster.containsValue(String.valueOf(name));
    }

    public static InetSocketAddress getAddressByName(String name){
        if (getUser(name)){
            for (Map.Entry<InetSocketAddress, String> entry : roster.entrySet()) {
                InetSocketAddress key = entry.getKey();
                String value = entry.getValue();
                if (name.equalsIgnoreCase(value))
                    return key;
            }
        }
        return null;
    }
    //todo

}
