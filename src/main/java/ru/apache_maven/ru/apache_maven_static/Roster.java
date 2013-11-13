package ru.apache_maven.ru.apache_maven_static;

import ru.apache_maven.CommonEnum;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Roster {
    private static HashMap<InetAddress, String> roster;

    public static void initalize() {
        roster = new HashMap<InetAddress, String>();
    }

    public static void setPerson(InetAddress address, String name) {
        roster.put(address, name);
    }

    public static void showAll() {
        Collection<String> c = roster.values();
        for (String type : c) System.out.println(type);

    }

    public static void setName(InetAddress key, String name) {
        roster.put(key, name);
    }

    public static String getNameByInetAddress(InetAddress ia) {
        String name = roster.get(ia);
        if (name == null)
            name = String.valueOf(CommonEnum.UNKNOWN_NAME);
        return name;
    }
    //todo

}
