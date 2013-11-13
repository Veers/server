package ru.apache_maven.ru.apache_maven_static;

public class Printer {
    public static void print(Object ob){
        System.out.print(ob);
    }
    public static void printLine(Object ob){
        System.out.println(ob);
    }
    /*
        not tested
     */
    public static void printSpacesLikeNewLine(Object obj){
        String str = String.valueOf(obj);
        int count = str.length();
        while (count > 0){
            System.out.println(str.substring(0, str.indexOf(" ")));
            str = str.substring(str.indexOf(" ") + 1);
            count = str.length();
        }
    }
}
