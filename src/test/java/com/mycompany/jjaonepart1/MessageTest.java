/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.jjaonepart1;

import java.io.File;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {

    private static final String TEST_JSON_PATH = "target/test-stored-messages.json";

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    // Runs before every test so each test starts cleanly with empty lists and a test JSON file.
    @BeforeEach
    public void setUp() {
        Message.setJsonFilePath(TEST_JSON_PATH);
        Message.clearAllMessages();
        Message.setSender("+27838968976");

        // Test Message 1: Sent.
        message1 = new Message(
                "1111111111",
                1,
                "+27834557896",
                "Did you get the cake?"
        );
        message1.sendMessage(new Scanner("1\n"));

        // Test Message 2: Stored.
        message2 = new Message(
                "2222222222",
                2,
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        );
        message2.sendMessage(new Scanner("3\n"));

        // Test Message 3: Disregarded.
        message3 = new Message(
                "3333333333",
                3,
                "+27834484567",
                "Yohoooo, I am at your gate."
        );
        message3.sendMessage(new Scanner("2\n"));

        // Test Message 4: Sent.
        message4 = new Message(
                "0838884567",
                4,
                "0838884567",
                "It is dinner time!"
        );
        message4.sendMessage(new Scanner("1\n"));

        // Test Message 5: Stored.
        message5 = new Message(
                "5555555555",
                5,
                "+27838884567",
                "Ok, I am leaving without you."
        );
        message5.sendMessage(new Scanner("3\n"));
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_JSON_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
        Message.resetJsonFilePath();
    }

    // Creates text with the selected number of characters.
    private String makeLongMessage(int length) {
        String text = "";

        for (int i = 0; i < length; i++) {
            text += "A";
        }

        return text;
    }

    @Test
    public void testMessageLengthSuccess() {
        Message message = new Message(
                "0000000000",
                0,
                "+27718693002",
                makeLongMessage(250)
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageLengthFailure() {
        Message message = new Message(
                "0000000000",
                0,
                "+27718693002",
                makeLongMessage(251)
        );

        assertEquals(
                "Message exceeds 250 characters by 1, please reduce size.",
                message.checkMessageLength()
        );
    }

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

    @Test
    public void testMessageID() {
        String id = Message.generateMessageID();

        assertEquals(10, id.length());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
                "0000000000",
                1,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message successfully sent.",
                message.sendMessage(new Scanner("1\n"))
        );
    }

    @Test
    public void testDiscardMessage() {
        Message message = new Message(
                "0000000000",
                2,
                "+27718693002",
                "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.sendMessage(new Scanner("2\n"))
        );
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(
                "0000000000",
                3,
                "+27718693002",
                "Reminder: meeting starts at 10."
        );

        assertEquals(
                "Message successfully stored.",
                message.sendMessage(new Scanner("3\n"))
        );
    }

    // Part 3: tests that sent-message list contains messages 1 and 4.
    @Test
    public void testSentMessagesArray() {
        assertEquals(2, Message.getSentMessages().size());
        assertEquals(
                "Did you get the cake?",
                Message.getSentMessages().get(0).getMessage()
        );
        assertEquals(
                "It is dinner time!",
                Message.getSentMessages().get(1).getMessage()
        );
    }

    // Part 3: tests disregarded and stored lists + IDs and hashes tracking.
    @Test
    public void testDisregardedStoredAndTrackingLists() {
        assertEquals(1, Message.getDiscardedMessages().size());
        assertEquals(
                "Yohoooo, I am at your gate.",
                Message.getDiscardedMessages().get(0).getMessage()
        );

        assertEquals(2, Message.getStoredMessages().size());
        assertTrue(Message.getMessageIDs().contains("2222222222"));
        assertTrue(Message.getMessageHashes().contains(message2.getMessageHash()));
    }

    // Part 3: tests stored message contacts display (submenu option 1).
    @Test
    public void testDisplayStoredMessageContacts() {
        String expected =
                "Sender: +27838968976\n"
                + "Recipient: +27838884567\n\n"
                + "Sender: +27838968976\n"
                + "Recipient: +27838884567\n\n";

        assertEquals(expected, Message.displayStoredMessageContacts());
    }

    // Part 3: tests that the longest stored message is message 2.
    @Test
    public void testLongestStoredMessage() {
        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                Message.getLongestStoredMessage()
        );
    }

    // Part 3: searches for message 4 using its message ID.
    @Test
    public void testSearchMessageID() {
        assertEquals(
                "It is dinner time!",
                Message.findMessageByID("0838884567")
        );
    }

    // Part 3: searches for all messages for one recipient.
    @Test
    public void testSearchMessagesByRecipient() {
        assertEquals(
                "Where are you? You are late! I have asked you to be on time."
                + "\nOk, I am leaving without you.",
                Message.findMessagesByRecipient("+27838884567")
        );
    }

    // Part 3: deletes stored message 2 using its message hash.
    @Test
    public void testDeleteMessageByHash() {
        assertEquals(
                "Message: \"Where are you? You are late! "
                + "I have asked you to be on time.\" successfully deleted.",
                Message.deleteMessageByHash(message2.getMessageHash())
        );

        assertEquals(1, Message.getStoredMessages().size());
        assertFalse(Message.getMessageHashes().contains(message2.getMessageHash()));
    }

    // Part 3: tests the sent-messages report (messages 1 and 4).
    @Test
    public void testSentMessageReport() {
        String expected =
                "----- SENT MESSAGE REPORT -----\n"
                + "Message Hash: 11:1:DIDCAKE\n"
                + "Recipient: +27834557896\n"
                + "Message: Did you get the cake?\n\n"
                + "Message Hash: 08:4:ITTIME\n"
                + "Recipient: 0838884567\n"
                + "Message: It is dinner time!\n\n";

        assertEquals(expected, Message.createSentMessageReport());
    }

    // Part 3: tests the stored-messages report (messages 2 and 5).
    @Test
    public void testStoredMessageReport() {
        String expected =
                "----- STORED MESSAGE REPORT -----\n"
                + "Message Hash: 22:2:WHERETIME\n"
                + "Recipient: +27838884567\n"
                + "Message: Where are you? You are late! "
                + "I have asked you to be on time.\n\n"
                + "Message Hash: 55:5:OKYOU\n"
                + "Recipient: +27838884567\n"
                + "Message: Ok, I am leaving without you.\n\n";

        assertEquals(expected, Message.createStoredMessageReport());
    }
}
