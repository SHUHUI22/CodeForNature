/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codefornature;

/**
 *
 * @author ACER
 */

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashing {
    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    public static boolean PasswordCheck(String plainPassword, String hashed) {
        return BCrypt.checkpw(plainPassword, hashed);
    }
}
