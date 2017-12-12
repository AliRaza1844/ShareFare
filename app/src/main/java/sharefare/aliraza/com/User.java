package sharefare.aliraza.com;

import java.util.ArrayList;

/**
 * Created by Ali_Raza on 12/11/2017.
 */

public class User {
    private static final User user = new User();
    public UserAccount account;
    public UserProfile profile;
    private float ranking;


    private User(){
        account = UserAccount.getAccount();
        profile = UserProfile.getProfile();
        ranking = 0;
    }

    public static User getUser(){
        return user;
    }
}
