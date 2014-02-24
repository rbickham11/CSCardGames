package cardgameslib.chatserver;

import java.util.EventObject;

/**
 * Defines an event to be sent when a new chat message arrives
 * @author Ryan Bickham
 */
public class ChatEvent extends EventObject {
    private final String message;
    
    public ChatEvent(Object source, String msg) {
        super(source);
        message = msg;
    }
    
    public String getMessage() {
        return message;
    }
}