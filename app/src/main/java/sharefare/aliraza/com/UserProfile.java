package sharefare.aliraza.com;

import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ali_Raza on 12/11/2017.
 */

public class UserProfile {
    private static final UserProfile profile= new UserProfile();
    private String firstName;
    private String lastName;
    private String cnic;
    private String address;
    private String phoneNumber;
    private String image;
    private int gender;
    private String dob;
    private int age;

    public UserProfile(){}


    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public static UserProfile getProfile(){
        return profile;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setCnic(String cnic){
        this.cnic = cnic;
    }

    public String getCnic(){
        return cnic;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setGender(int gender){
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void readFromDB(String username){

    }
}
