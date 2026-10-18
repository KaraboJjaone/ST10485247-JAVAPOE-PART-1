/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.jjaonepart1;

import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
    public MessageTest() {
    }
    
    // Makes a message with the requested number of characters
    private String makeLongMessage(int length) {

        String text = "";

        for (int i = 0; i < length; i++) {
            text += "A";
        }

        return text;
    }

    // Test: message with 250 characters is accepted
    @Test
    public void testMessageLengthSuccess() {

        String text = makeLongMessage(250);

        Message message = new Message(
                "0000000000",
                0,
                "+27718693002",
                text
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    // Test: message with more than 250 characters is rejected
    @Test
    public void testMessageLengthFailure() {

        String text = makeLongMessage(251);

        Message message = new Message(
                "0000000000",
                0,
                "+27718693002",
                text
        );

        assertEquals(
                "Message exceeds 250 characters by 1, please reduce size.",
                message.checkMessageLength()
        );
    }

    // Test: correctly formatted recipient number
    @Test
    public void testRecipientCellSuccess() {

        Message message = new Message(
                "0000000000",
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    // Test: incorrectly formatted recipient number
    @Test
    public void testRecipientCellFailure() {

        Message message = new Message(
                "0000000000",
                0,
                "0837156302",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.",
                message.checkRecipientCell()
        );
    }

    // Test: message hash is created correctly
    @Test
    public void testMessageHash() {

        Message message = new Message(
                "0000000000",
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash()
        );
    }

    // Test: message ID has 10 characters
    @Test
    public void testMessageID() {

        String id = Message.generateMessageID();

        assertTrue(id.length() <= 10);
    }

    // Test: user selects Send Message
    @Test
    public void testSendMessage() {

        Message message = new Message(
                "0000000000",
                1,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        // Simulates the user typing 1
        Scanner input = new Scanner("1\n");

        assertEquals(
                "Message successfully sent.",
                message.sendMessage(input)
        );
    }

    // Test: user selects Disregard Message
    @Test
    public void testDiscardMessage() {

        Message message = new Message(
                "0000000000",
                2,
                "+27718693002",
                "Hi Keegan, did you receive the payment?"
        );

        // Simulates the user typing 2
        Scanner input = new Scanner("2\n");

        assertEquals(
                "Press 0 to delete the message.",
                message.sendMessage(input)
        );
    }

    // Test: user selects Store Message
    @Test
    public void testStoreMessage() {

        Message message = new Message(
                "0000000000",
                3,
                "+27718693002",
                "Reminder: meeting starts at 10."
        );

        // Simulates the user typing 3
        Scanner input = new Scanner("3\n");

        assertEquals(
                "Message successfully stored.",
                message.sendMessage(input)
        );
    }
}
