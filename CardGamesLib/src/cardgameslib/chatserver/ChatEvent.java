package cardgameslib.chatserver;

import java.util.EventObject;

/**
 * 
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