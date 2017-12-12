package sharefare.aliraza.com;

public class UserAccount {
    private static final UserAccount user = new UserAccount();

    private String username = "";
    private String password = "";

    private UserAccount(){}

    public static UserAccount getAccount(){
        return user;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
