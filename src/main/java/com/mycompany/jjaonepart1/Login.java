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

    // Setters
    public void setFirstName(String name) { firstName = name; }
    public void setLastName(String surname) { lastName = surname; }
    public void setUserName(String name) { userName = name; }
    public void setPassword(String word) { password = word; }
    public void setCellNumber(String number) { cellNumber = number; }

    // 1. Check Username
    public boolean checkUserName() {
        return userName.contains("_") && userName.length() <= 5;
    }

    // 2. Check Password
    public boolean checkPasswordComplexity() {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char letter = password.charAt(i);

            if (Character.isUpperCase(letter)) hasCapital = true;
            if (Character.isDigit(letter)) hasNumber = true;
            if (!Character.isLetterOrDigit(letter)) hasSpecial = true;
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    // 3. Check Cell Number
    public boolean checkCellPhoneNumber() {
        return cellNumber.startsWith("+27") && cellNumber.length() == 12;
    }

    // 4. Registration Result Message
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

    // 5. Check Login Match
    public boolean loginUser(String user, String pass) {
        return user.equals(userName) && pass.equals(password);
    }

    // 6. Login Result Message
    public String returnLoginStatus(boolean isLogged) {
        if (isLogged) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}

    
