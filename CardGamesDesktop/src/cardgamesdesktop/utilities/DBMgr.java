package cardgamesdesktop.utilities;

import java.sql.*;

public class DBMgr {
    private static final String URL = "jdbc:mysql://localhost:3306/cardgamesdb";
    private static final String USER = "root";
    private static final String PASS = "cgadmin490";
    
    private String query;
    private Statement statement;
    private ResultSet resultSet;
    
    public DBMgr() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            statement = con.createStatement();
            
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void addUser(String userName, String password, String email) {
        try {
            String saltedHash = PasswordHash.createHash(password);
            query = "INSERT INTO user (userName, password, email, displayName) VALUES ('" + userName + "','" + saltedHash + "','" + email + "','" + userName + "')";
            statement.executeUpdate(query);
            System.out.println("User: " + userName + " added.");
            System.out.println("Password: " + saltedHash + " added. ");
            System.out.println("Email: " + email + " added. ");
            System.out.println("Display name: " + userName + " added.");
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public boolean userExists(String username) throws SQLException {
        query = "SELECT * FROM user WHERE userName = '" + username + "' LIMIT 1";
        resultSet = statement.executeQuery(query);
        return resultSet.next();     
    }
    
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
    
    public void setDisplayName(int userId, String newDisplayName) {
        try {
            query = "UPDATE user SET displayName = '" + newDisplayName + "' WHERE id = '" + userId + "'";
            statement.executeUpdate(query);
        }
        catch(SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

        
    public void setEmail(int userId, String newEmail) {
        try {
            query = "UPDATE user SET email = '" + newEmail + "' WHERE id = '" + userId + "'";
            statement.executeUpdate(query);
        }
        catch(SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
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
