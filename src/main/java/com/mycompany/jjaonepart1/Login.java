/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jjaonepart1;

/**
 *
 * @author Student
 */
class Login {
    String firstName;
    String lastName;
    String userName;
    String password;
    String cellNumber;

    //methods to save the user input into variables
    public void setFirstName(String name) { firstName = name; }
    public void setLastName(String surname) { lastName = surname; }
    public void setUserName(String name) { userName = name; }
    public void setPassword(String word) { password = word; }
    public void setCellNumber(String number) { cellNumber = number; }

    //Check Username
    //Returns true if username has an underscore (_) and is 5 or fewer characters.
    public boolean checkUserName() {
        return userName.contains("_") && userName.length() <= 5;
    }

    //Check Password
    //Returns true if the password is at least 8 characters long and contains a capital letter, a number, and a special character.
    public boolean checkPasswordComplexity() {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        // Loop through each character to test for rules
        for (int i = 0; i < password.length(); i++) {
            char letter = password.charAt(i);

            if (Character.isUpperCase(letter)) hasCapital = true;
            if (Character.isDigit(letter)) hasNumber = true;
            if (!Character.isLetterOrDigit(letter)) hasSpecial = true;
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    //Check Cell Number
    //Returns true if cell number starts with +27 and is exactly 12 characters long.
    public boolean checkCellPhoneNumber() {
        return cellNumber.startsWith("+27") && cellNumber.length() == 12;
    }

    //Registration Result Message
    //Checks all details and returns an error message if any fail or a success message if all are valid.
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number is incorrectly formatted.";
        }
        return "User registered successfully.";
    }

    //Check Login Match
    //Returns true if the entered username and password match stored details.
    public boolean loginUser(String user, String pass) {
        return user.equals(userName) && pass.equals(password);
    }

    //Login Result Message
    //Returns a welcome message if logged in, or an error message if failed.
    public String returnLoginStatus(boolean isLogged) {
        if (isLogged) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}

    
