/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jjaonepart1;
import java.util.Scanner;
/**
 *
 * @author Student
 */

public class JjaonePart1 {

    public static void main(String[] args) {
        Scanner myInput = new Scanner(System.in);
        Login login = new Login();

        //Ask user to enter first Name
        System.out.print("Please enter your first name: ");
        login.setFirstName(myInput.next());

        //Ask user to enter last name
        System.out.print("Please enter your last name: ");
        login.setLastName(myInput.next());

        //Ask the user to enter username and keep asking if it's wrongly formatted
        System.out.print("Please enter your username: ");
        login.setUserName(myInput.next());

        while (!login.checkUserName()) {
            System.out.println("Username is not correctly formatted; please"
                    + " ensure thet your username contains an under"
                    + "underscore and is no more than five characters in length");
            System.out.print("Please enter your username: ");
            login.setUserName(myInput.next());
        }
        System.out.println("Username successfully captured.");

        //Ask the user to enter password and keep asking if it's wrongly formatted
        System.out.print("Please enter your password: ");
        login.setPassword(myInput.next());

        while (!login.checkPasswordComplexity()) {
            System.out.println("Password is not correctly formatted; please ensure that"
                    + "the password contains at least eight characters, a capital letter, "
                    + "a number, and a special character.");
            System.out.print("Please enter your password: ");
            login.setPassword(myInput.next());
        }
        System.out.println("Password successfully captured.");

        //Ask the user to enter cell phone and keep asking if it's wrong
        System.out.print("Please enter your cell number: ");
        login.setCellNumber(myInput.next());

        while (!login.checkCellPhoneNumber()) {
            System.out.println("Cell phone number is incorrectly formatted or does not contain"
                    + "international code.");
            System.out.print("Please enter your cell number: ");
            login.setCellNumber(myInput.next());
        }
        System.out.println("Cell phone number successfully added.");

        // Show registration result
        System.out.println(login.registerUser());

        //Login Section Loop
        System.out.println("\n--- Login ---");

        System.out.print("Enter your username: ");
        String enteredUser = myInput.next();

        System.out.print("Enter your password: ");
        String enteredPass = myInput.next();

        boolean loggedIn = login.loginUser(enteredUser, enteredPass);
        System.out.println(login.returnLoginStatus(loggedIn));

        //Keep asking for login details if they were incorrect
        while (!loggedIn) {
            System.out.print("Enter your username: ");
            enteredUser = myInput.next();

            System.out.print("Enter your password: ");
            enteredPass = myInput.next();

            loggedIn = login.loginUser(enteredUser, enteredPass);
            System.out.println(login.returnLoginStatus(loggedIn));
        }

    }
}

        
