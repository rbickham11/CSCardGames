package cardgamesdesktop.utilities;

/**
 * Holds user information in a static context for use throughout the session
 * @author Ryan
 */
public class UserSessionVars {
    private static int userId;
    private static String username;
    private static String email;
    private static String displayName;
    
    public static int getUserId() { return userId; }   
    public static String getUsername() { return username; }    
    public static String getEmail() { return email; }
    public static String getDisplayName() { return displayName; }
    
    public static void set(int id, String name, String em, String dn) {
        userId = id;
        username = name;
        email = em;
        displayName = dn;
    }
    
    public static void clear() {
        userId = 0;
        username = null;
        email = null;
        displayName = null;
    }
}
