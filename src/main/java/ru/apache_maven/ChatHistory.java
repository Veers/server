package ru.apache_maven;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {
    private List<Message> history;

    public ChatHistory() {
        this.history = new ArrayList<Message>(Config.HISTORY_SIZE);
    }

    public void addMessage(Message message) {
        if (this.history.size() > Config.HISTORY_SIZE) {
            this.history.remove(0);
        }
        this.history.add(message);
    }

    public List<Message> getHistory() {
        return this.history;
    }


}
