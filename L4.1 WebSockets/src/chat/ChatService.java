package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ChatService {
    private Set<ChatWebSocket> webSockets;

    public ChatService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendMessage(String data) {
        for (ChatWebSocket user : webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e) {
                System.err.println("Error sending message to user: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void add(ChatWebSocket webSocket) {
        if (webSocket != null) {
            webSockets.add(webSocket);
        } else {
            System.err.println("Attempted to add a null WebSocket to the ChatService.");
        }
    }

    public void remove(ChatWebSocket webSocket) {
        if (webSocket != null) {
            webSockets.remove(webSocket);
        } else {
            System.err.println("Attempted to remove a null WebSocket from the ChatService.");
        }
    }
}