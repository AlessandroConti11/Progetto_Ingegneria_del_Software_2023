package it.polimi.ingsw.client;

import it.polimi.ingsw.utils.Chat;

import java.util.HashMap;

public class ChatController {
    private Chat publicChat = new Chat();
    private HashMap<String, Chat> privateChats = new HashMap<>();

    public Chat getPublicChat() {
        return publicChat;
    }

    public Chat getPrivateChat (String username) {
        return privateChats.get(username);
    }

    public HashMap<String, Chat> getPrivateChats() {
        return privateChats;
    }
}