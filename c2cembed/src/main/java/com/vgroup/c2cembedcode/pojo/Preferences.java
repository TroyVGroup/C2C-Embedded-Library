package com.vgroup.c2cembedcode.pojo;

public class Preferences{
    public boolean verifyemail;
    public boolean contact;
    public boolean name;
    public boolean verifycontact;
    public boolean message;
    public boolean email;


    public Boolean isVerificationRequired(){
        if (contact == false && name == false && message == false && email == false){
            return false;
        }
        return true;
    }
}
