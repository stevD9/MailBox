package stef.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String role;
    
    public User(){}
    
    public User(String username, String password, String fname, String lname, String role){
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
    }
    
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswprd(String password) {
        this.password = password;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isAdmin(){
        if (this.role.equals("admin")){
            return true;
        } else {
            return false;
        }
    }
    
}
