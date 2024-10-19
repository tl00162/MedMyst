package edu.westga.medmyst.project.model;


/**
 * Represents a login with a username and password.
 * 
 * @author tl00162
 */
public class Login {

    private String username;
    private String password;
    
    /**
     * Constructs a new Login instance with the given username and a filler password
     *
     * @param username the username for this login, must not be null or empty
     * @throws IllegalArgumentException if the username or password is null or empty
     */
    public Login(String username) {
        this(username, "password");
    }

    /**
     * Constructs a new Login instance with the given username and password.
     *
     * @param username the username for this login, must not be null or empty
     * @param password the password for this login, must not be null or empty
     * @throws IllegalArgumentException if the username or password is null or empty
     */
    public Login(String username, String password) {
        if (username == null || username.length() == 0) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username for this login.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the password for this login.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
