package cardgamesdesktop.utilities;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * For managing the connection to the database and querying the database
 * @author Ryan
 */
public class DBMgr {
    //Use for local database
    private static final String URL = "jdbc:mysql://localhost:3307/cardgamesdb";
    private static final String USER = "root";
    private static final String PASS = "cgadmin490";
    //**
    
    private String query;
    private Statement statement;
    private ResultSet resultSet;
    
    /**
     * Connects to the database 
     */
    public DBMgr() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Use for remote database
            Properties config = new Properties();
            config.load(new FileInputStream("config.properties"));
            Connection con = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password"));
           //Use for local database
           //Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            statement = con.createStatement();
            
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Adds a new user to the database, including a salted password for security.
     * @param userName The username for the new user
     * @param password The password for the new user
     * @param email  The email address for the new user
     */
    public void addUser(String userName, String password, String email) {
        try {
            //Get salted password hash
            String saltedHash = PasswordHash.createHash(password);
            query = "INSERT INTO user (userName, password, email, displayName) VALUES ('" + userName + "','" + saltedHash + "','" + email + "','" + userName + "')";
            statement.executeUpdate(query);
            System.out.println("User: " + userName + " added.");
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Checks if a given user exists in the database
     * @param username The username to check
     * @return true if the user exists
     * @throws SQLException 
     */
    public boolean userExists(String username) throws SQLException {
        query = "SELECT * FROM user WHERE userName = '" + username + "' LIMIT 1";
        resultSet = statement.executeQuery(query);
        return resultSet.next();     
    }
    
    /**
     * Checks if a username and password combination is valid
     * @param username The username to check
     * @param password The password to check
     * @return true if the user is valid
     */
    public boolean validateUser(String username, String password) {
        String correctHash;
        
        try {
            if(userExists(username)) {
                correctHash = resultSet.getString("password");
                if(PasswordHash.validatePassword(password, correctHash)) {
                    System.out.println("Login Successful!");
                    setSession(username);
                    return true;
                }
                System.out.println("Password incorrect.");
                return false;
            }
            System.out.println("User " + username + " does not exist.");
            return false;
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
            return false;
        }
    }
    
    /**
     * Sets a new password for a user
     * @param userId The user id to set the new password for
     * @param newPassword The new password
     */
    public void setPassword(int userId, String newPassword) {
        try {
            String saltedHash = PasswordHash.createHash(newPassword);
            query = "UPDATE user SET password = '" + saltedHash + "' WHERE id = '" + userId + "'";
            statement.executeUpdate(query);
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Sets a new display name for a user
     * @param userId The user id to set the new display name for
     * @param newDisplayName The new display name
     */
    public void setDisplayName(int userId, String newDisplayName) {
        try {
            query = "UPDATE user SET displayName = '" + newDisplayName + "' WHERE id = '" + userId + "'";
            statement.executeUpdate(query);
        }
        catch(SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Sets a new email for a user
     * @param userId The user id to set the new email for
     * @param newEmail The new email
     */
    public void setEmail(int userId, String newEmail) {
        try {
            query = "UPDATE user SET email = '" + newEmail + "' WHERE id = '" + userId + "'";
            statement.executeUpdate(query);
        }
        catch(SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Sets session variables with the current values in the database
     * @param username The username of the user to set values for
     */
    public void setSession(String username) {
        try {
            if(userExists(username))
                UserSessionVars.set(resultSet.getInt("id"), resultSet.getString("userName"), resultSet.getString("email"), resultSet.getString("displayName"));
            else 
                throw new IllegalArgumentException("User " + username + " does not exist.");
        }
        catch(SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace(System.out);
        }
    }
}
