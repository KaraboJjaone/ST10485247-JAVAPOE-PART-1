/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jjaonepart1;
import java.util.ArrayList; // Allows the program to store a list of messages
import java.util.Random;    // Allows the program to create random numbers
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Message {
     // Variables for one message
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private String messageStatus;

    // Lists for sent, discarded, and stored messages
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> discardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();

    // Constructor
    public Message(String messageID, int messageNumber,
            String recipient, String message) {

        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
        this.messageStatus = "";
    }

    // Creates a random 10-digit message ID
    public static String generateMessageID() {
        Random random = new Random();
        String id = "";

        for (int i = 0; i < 10; i++) {
            id += random.nextInt(10);
        }

        return id;
    }

    // Checks that the message ID has 10 or fewer characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks the recipient cell number
    public String checkRecipientCell() {

        if (recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.";
    }

    // Checks whether the message is 250 characters or fewer
    public String checkMessageLength() {

        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int extraCharacters = message.length() - 250;

        return "Message exceeds 250 characters by "
                + extraCharacters + ", please reduce size.";
    }

    // Creates the message hash
    public String createMessageHash() {

        String firstWord = "";
        String lastWord = "";

        String trimmedMessage = message.trim();

        if (!trimmedMessage.isEmpty()) {
            String[] words = trimmedMessage.split("\\s+");

            firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");

            lastWord = words[words.length - 1]
            .replaceAll("[^a-zA-Z0-9]", "");
        }

        messageHash = messageID.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord + lastWord;

        return messageHash.toUpperCase();
    }

    // Lets user send, discard, or store the message
    public String sendMessage(Scanner input) {

        System.out.println("\n1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message");
        System.out.print("Choose an option: ");

        String choice = input.nextLine();

        switch (choice) {
            case "1":
                messageStatus = "Sent";
                sentMessages.add(this);
                return "Message successfully sent.";

            case "2":
                messageStatus = "Discarded";
                discardedMessages.add(this);
                return "Press 0 to delete the message.";

            case "3":
                messageStatus = "Stored";
                storedMessages.add(this);
                return "Message successfully stored.";

            default:
                return "Invalid option.";
        }
    }

    // Returns all messages sent
    public static String printMessages() {

        String output = "";

        for (Message currentMessage : sentMessages) {
            output += "Message ID: " + currentMessage.messageID + "\n";
            output += "Message Hash: " + currentMessage.messageHash + "\n";
            output += "Recipient: " + currentMessage.recipient + "\n";
            output += "Message: " + currentMessage.message + "\n\n";
        }

        return output;
    }

    // Returns the total number of messages sent
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    // Getter methods
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessageStatus() {
        return messageStatus;
    }
}

