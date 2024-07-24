/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codefornature;

/**
 *
 * @author ACER
 */
public class LoginAndRegistration {
    public static void main(String[] args) {
        Login LoginFrame=new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();  // Resize the frame based on the preferred sizes of components
        LoginFrame.setLocationRelativeTo(null); // Center the frame on the screen
    }
}
