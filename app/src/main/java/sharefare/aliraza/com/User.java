package sharefare.aliraza.com;

import java.util.ArrayList;

/**
 * Created by Ali_Raza on 12/11/2017.
 */

public class User {
    private static final User user = new User();
    public UserAccount account;
    public UserProfile profile;
    public Wallet wallet;
    private float ranking;
    private String profile_id;


    private User(){
        account = UserAccount.getAccount();
        profile = UserProfile.getProfile();
        wallet = Wallet.getWallet();
    }

    public void setRanking(float ranking) {
        this.ranking = ranking;
    }

    public float getRanking() {
        return ranking;
    }

    public static User getUser(){
        return user;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }
}
