/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jjaonepart1;

// Imports allow the program to use lists, random numbers, files and JSON.
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Message {

    // Variables that store information about one message.
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private String messageStatus;

    // Default and active JSON file path where stored messages are saved.
    private static final String DEFAULT_JSON_FILE = "stored_messages.json";
    private static String jsonFilePath = DEFAULT_JSON_FILE;

    // The sender shown when stored messages are displayed.
    private static String sender = "You";

    // Lists that store messages according to their status.
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> discardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();

    // Lists that store all message hashes and message IDs.
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();

    // Constructor: creates one complete message object.
    public Message(String messageID, int messageNumber,
            String recipient, String message) {

        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;

        // Create the hash as soon as a message is created.
        this.messageHash = createMessageHash();

        // The message has no status until the user chooses an option.
        this.messageStatus = "";
    }

    // Allows tests to use a separate JSON file so real stored_messages.json is untouched.
    public static void setJsonFilePath(String path) {
        if (path != null && !path.trim().isEmpty()) {
            jsonFilePath = path.trim();
        }
    }

    // Resets the JSON file path back to the default stored_messages.json.
    public static void resetJsonFilePath() {
        jsonFilePath = DEFAULT_JSON_FILE;
    }

    // Creates a random 10-digit message ID.
    public static String generateMessageID() {
        Random random = new Random();
        String id = "";

        // Repeat 10 times and add one random digit each time.
        for (int i = 0; i < 10; i++) {
            id += random.nextInt(10);
        }

        return id;
    }

    // Checks whether the message ID has 10 or fewer characters.
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks whether the recipient number has +27 and 9 digits after it.
    public String checkRecipientCell() {
        if (recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.";
    }

    // Checks that a message is not longer than 250 characters.
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        // Calculates how many characters the user must remove.
        return "Message exceeds 250 characters by "
                + (message.length() - 250)
                + ", please reduce size.";
    }

    // Creates the message hash using the ID, number, first word and last word.
    public String createMessageHash() {
        String firstWord = "";
        String lastWord = "";
        String trimmedMessage = message.trim();

        // Only split the message into words when it is not empty.
        if (!trimmedMessage.isEmpty()) {
            String[] words = trimmedMessage.split("\\s+");

            // Remove punctuation from the first and last words.
            firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
            lastWord = words[words.length - 1]
                    .replaceAll("[^a-zA-Z0-9]", "");
        }

        String firstTwoCharacters = "";

        // Make sure the ID has at least two characters before using substring.
        if (messageID.length() >= 2) {
            firstTwoCharacters = messageID.substring(0, 2);
        }

        // Build the hash and return it in capital letters.
        return (firstTwoCharacters
                + ":" + messageNumber
                + ":" + firstWord + lastWord).toUpperCase();
    }

    // Lets the user decide whether to send, disregard or store a message.
    public String sendMessage(Scanner input) {
        System.out.println("Choose an option:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");

        String choice = input.nextLine();

        switch (choice) {
            case "1":
                // Add the message to the sent-message list.
                messageStatus = "Sent";
                sentMessages.add(this);
                addMessageTracking(this);
                return "Message successfully sent.";

            case "2":
                // Add the message to the disregarded-message list.
                messageStatus = "Discarded";
                discardedMessages.add(this);
                addMessageTracking(this);
                return "Press 0 to delete the message.";

            case "3":
                // Add the message to the stored-message list.
                messageStatus = "Stored";
                storedMessages.add(this);
                addMessageTracking(this);

                try {
                    // Save every stored message into the JSON file.
                    storeMessage();
                    return "Message successfully stored.";
                } catch (IOException error) {
                    return "Message could not be stored.";
                }

            default:
                return "Invalid option.";
        }
    }

    // Adds a message ID and hash to their tracking lists.
    private static void addMessageTracking(Message currentMessage) {
        if (!messageIDs.contains(currentMessage.messageID)) {
            messageIDs.add(currentMessage.messageID);
        }

        if (!messageHashes.contains(currentMessage.messageHash)) {
            messageHashes.add(currentMessage.messageHash);
        }
    }

    // Writes all stored messages into the active JSON file.
    @SuppressWarnings("unchecked")
    private static void storeMessage() throws IOException {
        JSONArray messagesJson = new JSONArray();

        // Change every stored Message object into a JSON object.
        for (Message currentMessage : storedMessages) {
            JSONObject messageJson = new JSONObject();

            messageJson.put("messageID", currentMessage.messageID);
            messageJson.put("messageNumber", currentMessage.messageNumber);
            messageJson.put("recipient", currentMessage.recipient);
            messageJson.put("message", currentMessage.message);
            messageJson.put("messageHash", currentMessage.messageHash);
            messageJson.put("messageStatus", currentMessage.messageStatus);

            // Add the JSON object to the JSON array.
            messagesJson.add(messageJson);
        }

        // Create or overwrite the JSON file.
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(messagesJson.toJSONString());
        }
    }

    // JSON reading adapted from Fadatare, R. (2019) JSON.simple Tutorial - Read
    // and Write JSON in Java. Available at:
    // https://www.javaguides.net/2019/07/jsonsimple-tutorial-read-and-write-json-in-java.html
    // (Accessed: 17 November 2026).
    public static boolean loadStoredMessagesFromJson() {
        File file = new File(jsonFilePath);

        // If no JSON file exists yet, there are simply no stored messages.
        if (!file.exists()) {
            return true;
        }

        try (FileReader reader = new FileReader(file)) {
            JSONParser parser = new JSONParser();
            JSONArray messagesJson = (JSONArray) parser.parse(reader);

            // Clear the old list before loading the messages again.
            storedMessages.clear();

            // Read each JSON object and turn it back into a Message object.
            for (Object object : messagesJson) {
                JSONObject messageJson = (JSONObject) object;

                String id = String.valueOf(messageJson.get("messageID"));
                int number = Integer.parseInt(
                        String.valueOf(messageJson.get("messageNumber"))
                );
                String recipient = String.valueOf(
                        messageJson.get("recipient")
                );
                String messageText = String.valueOf(
                        messageJson.get("message")
                );

                Message storedMessage = new Message(
                        id, number, recipient, messageText
                );

                // Restore the original saved hash and status.
                storedMessage.messageHash = String.valueOf(
                        messageJson.get("messageHash")
                );
                storedMessage.messageStatus = "Stored";

                storedMessages.add(storedMessage);
                addMessageTracking(storedMessage);
            }

            return true;

        } catch (IOException | ParseException
                | NumberFormatException error) {
            return false;
        }
    }

    // Sets the sender shown in the stored messages display.
    public static void setSender(String senderNumber) {
        sender = senderNumber;
    }

    // Displays the sender and recipient for each stored message.
    public static String displayStoredMessageContacts() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        StringBuilder output = new StringBuilder();

        for (Message currentMessage : storedMessages) {
            output.append("Sender: ").append(sender).append("\n");
            output.append("Recipient: ")
                    .append(currentMessage.recipient)
                    .append("\n\n");
        }

        return output.toString();
    }

    // Finds and returns the longest stored message.
    public static String getLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        Message longestMessage = storedMessages.get(0);

        for (Message currentMessage : storedMessages) {
            if (currentMessage.message.length()
                    > longestMessage.message.length()) {
                longestMessage = currentMessage;
            }
        }

        return longestMessage.message;
    }

    // Looks for a message object using its message ID.
    private static Message findMessageObjectByID(String id) {
        for (Message currentMessage : sentMessages) {
            if (currentMessage.messageID.equals(id)) {
                return currentMessage;
            }
        }

        for (Message currentMessage : storedMessages) {
            if (currentMessage.messageID.equals(id)) {
                return currentMessage;
            }
        }

        return null;
    }

    // Searches for a message ID and returns only the message text.
    public static String findMessageByID(String id) {
        Message foundMessage = findMessageObjectByID(id);

        if (foundMessage == null) {
            return "Message ID not found.";
        }

        return foundMessage.message;
    }

    // Searches for a message ID and returns recipient and message details.
    public static String findMessageDetailsByID(String id) {
        Message foundMessage = findMessageObjectByID(id);

        if (foundMessage == null) {
            return "Message ID not found.";
        }

        return "Recipient: " + foundMessage.recipient
                + "\nMessage: " + foundMessage.message;
    }

    // Finds all sent or stored messages for one recipient.
    public static String findMessagesByRecipient(String recipient) {
        StringBuilder output = new StringBuilder();

        for (Message currentMessage : sentMessages) {
            if (currentMessage.recipient.equals(recipient)) {
                output.append(currentMessage.message).append("\n");
            }
        }

        for (Message currentMessage : storedMessages) {
            if (currentMessage.recipient.equals(recipient)) {
                output.append(currentMessage.message).append("\n");
            }
        }

        if (output.length() == 0) {
            return "No messages found for this recipient.";
        }

        return output.toString().trim();
    }

    // Deletes one stored message by using its message hash.
    public static String deleteMessageByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message currentMessage = storedMessages.get(i);

            if (currentMessage.messageHash.equals(hash)) {
                String deletedMessage = currentMessage.message;

                // Remove the message from all relevant lists.
                storedMessages.remove(i);
                messageHashes.remove(hash);
                messageIDs.remove(currentMessage.messageID);

                try {
                    // Update the JSON file after deleting the message.
                    storeMessage();

                    return "Message: \"" + deletedMessage
                            + "\" successfully deleted.";

                } catch (IOException error) {
                    return "Message could not be deleted.";
                }
            }
        }

        return "Message hash not found.";
    }

    // Creates a report containing all stored-message details.
    public static String createStoredMessageReport() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }

        StringBuilder report = new StringBuilder();
        report.append("----- STORED MESSAGE REPORT -----\n");

        for (Message currentMessage : storedMessages) {
            report.append("Message Hash: ")
                    .append(currentMessage.messageHash)
                    .append("\n");

            report.append("Recipient: ")
                    .append(currentMessage.recipient)
                    .append("\n");

            report.append("Message: ")
                    .append(currentMessage.message)
                    .append("\n\n");
        }

        return report.toString();
    }

    // Creates a report containing the details of all sent messages.
    public static String createSentMessageReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages found.";
        }

        StringBuilder report = new StringBuilder();
        report.append("----- SENT MESSAGE REPORT -----\n");

        for (Message currentMessage : sentMessages) {
            report.append("Message Hash: ")
                    .append(currentMessage.messageHash)
                    .append("\n");

            report.append("Recipient: ")
                    .append(currentMessage.recipient)
                    .append("\n");

            report.append("Message: ")
                    .append(currentMessage.message)
                    .append("\n\n");
        }

        return report.toString();
    }

    // Returns the number of messages which were sent.
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    // These getter methods allow other classes and tests to read the lists.
    public static ArrayList<Message> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }

    public static ArrayList<Message> getDiscardedMessages() {
        return new ArrayList<>(discardedMessages);
    }

    public static ArrayList<Message> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }

    public static ArrayList<String> getMessageHashes() {
        return new ArrayList<>(messageHashes);
    }

    public static ArrayList<String> getMessageIDs() {
        return new ArrayList<>(messageIDs);
    }

    // Clears the lists before each JUnit test starts.
    public static void clearAllMessages() {
        sentMessages.clear();
        discardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
    }

    // Getter methods return one detail from one message object.
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageStatus() {
        return messageStatus;
    }
}
