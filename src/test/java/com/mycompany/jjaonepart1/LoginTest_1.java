/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.jjaonepart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest_1 {

    Login login = new Login();

    // ==========================================
    // 1. BOOLEAN TESTS (assertTrue / assertFalse)
    // ==========================================

    @Test
    public void testCheckUserNameSuccess() {
        login.setUserName("kyl_1");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserNameFailure() {
        login.setUserName("kyle!!!!!!! ");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexitySuccess() {
        login.setPassword("Ch&&secake99!");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexityFailure() {
        login.setPassword("password");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumberSuccess() {
        login.setCellNumber("+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberFailure() {
        login.setCellNumber("08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginUserSuccess() {
        login.setUserName("kyl_1");
        login.setPassword("Ch&&secake99!");
        assertTrue(login.loginUser("kyl_1", "Ch&&secake99!"));
    }

    @Test
    public void testLoginUserFailure() {
        login.setUserName("kyl_1");
        login.setPassword("Ch&&secake99!");
        assertFalse(login.loginUser("wrong_user", "wrong_pass"));
    }

    // ==========================================
    // 2. STRING TESTS USING POE EXPECTED RESULTS
    // ==========================================

    @Test
    public void testRegisterUserBadUsernameMessage() {
        login.setUserName("kyle!!!!!!! ");
        login.setPassword("Ch&&secake99!");
        login.setCellNumber("+27838968976");

        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        String actual = login.registerUser();
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUserBadPasswordMessage() {
        login.setUserName("kyl_1");
        login.setPassword("password");
        login.setCellNumber("+27838968976");

        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        String actual = login.registerUser();
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUserBadCellMessage() {
        login.setUserName("kyl_1");
        login.setPassword("Ch&&secake99!");
        login.setCellNumber("08966553");

        String expected = "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        String actual = login.registerUser();
        assertEquals(expected, actual);
    }

    @Test
    public void testReturnLoginStatusSuccessMessage() {
        login.setFirstName("Kyle");
        login.setLastName("User");

        String expected = "Welcome Kyle User it is great to see you.";
        String actual = login.returnLoginStatus(true);
        assertEquals(expected, actual);
    }
}
