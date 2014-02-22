package cardgamesdesktop.utilities;

import java.sql.*;

public class DBMgr {
    private static final String URL = "jdbc:mysql://localhost:3306/cardgamesdb";
    private static final String USER = "root";
    private static final String PASS = "cgadmin490";
    
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
            String query = "INSERT INTO user (userName, password, email) VALUES ('" + userName + "','" + saltedHash + "','" + email + "')";
            statement.executeUpdate(query);
            System.out.println("User: " + userName + " added.");
            System.out.println("Password: " + saltedHash + " added. ");
            System.out.println("Email: " + email + " added. ");
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public boolean userExists(String username) throws SQLException {
        String query = "SELECT userName, password FROM user WHERE userName = '" + username + "' LIMIT 1";
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
}
