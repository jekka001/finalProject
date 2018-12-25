package com.epam.cruiseCompany.service;


import java.security.SecureRandom;
import org.jasypt.util.password.BasicPasswordEncryptor;
public class Encryption {

    public static int generationSalt(){
        return new SecureRandom().nextInt();
    }

    public static String encryption(String password){
        return new BasicPasswordEncryptor().encryptPassword(password);
    }

    public static boolean checkPassword(String userPassword, String bdPassword){
        return new BasicPasswordEncryptor().checkPassword(userPassword, bdPassword);
    }
}
