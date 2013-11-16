package ru.apache_maven;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {
    private static List<Message> history;

    public ChatHistory() {
        history = new ArrayList<Message>(Config.HISTORY_SIZE);
    }

    public static void addMessage(Message message) {
        if (history.size() > Config.HISTORY_SIZE) {
            history.remove(0);
        }
        history.add(message);
    }

    static List<Message> getHistory() {
        return history;
    }

}
