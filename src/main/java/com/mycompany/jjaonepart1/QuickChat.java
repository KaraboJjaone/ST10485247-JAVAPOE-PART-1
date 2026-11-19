/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jjaonepart1;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class QuickChat {

    // Starts QuickChat after the user has logged in.
    public static void startQuickChat(Scanner input) {

        // Load messages that were previously saved in stored_messages.json.
        if (!Message.loadStoredMessagesFromJson()) {
            System.out.println("Stored messages could not be loaded.");
        }

        System.out.println("Welcome to QuickChat.");

        // Ask the user how many new messages they want to enter.
        System.out.print("How many messages would you like to enter? ");
        int numberOfMessages = input.nextInt();

        // Clears the Enter key after nextInt().
        input.nextLine();

        boolean running = true;

        // Keep showing the main menu until the user selects Quit.
        while (running) {
            System.out.println("\n---------- QUICKCHAT ----------");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            String option = input.nextLine();

            switch (option) {
                case "1":
                    // Allows the user to enter the selected number of messages.
                    sendMessages(input, numberOfMessages);

                    // Prevents the user from entering the same selected amount again.
                    numberOfMessages = 0;
                    break;

                case "2":
                     // Displays a report of all sent messages.
                     System.out.println(Message.createSentMessageReport());
                     break;

                case "3":
                    // Ends the main-menu loop.
                    running = false;
                    System.out.println("\nThank you for using QuickChat.");
                    break;

                case "4":
                    // Opens the Part 3 stored-messages submenu.
                    storedMessagesMenu(input);
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please select 1, 2, 3 or 4."
                    );
            }
        }
    }

    // Displays the Part 3 submenu for stored messages.
    private static void storedMessagesMenu(Scanner input) {
        boolean viewingStoredMessages = true;

        while (viewingStoredMessages) {
            System.out.println("\n------- STORED MESSAGES -------");
            System.out.println("1. Display the sender and recipient of all stored messages.");
            System.out.println("2. Display the longest stored message.");
            System.out.println("3. Search for a message ID and display the corresponding recipient and message.");
            System.out.println("4. Search for all messages stored for a particular recipient.");
            System.out.println("5. Delete a message using the message hash.");
            System.out.println("6. Display a report that lists the full details of all stored messages.");
            System.out.println("7. Return to the main menu.");
            System.out.print("Choose an option: ");

            String option = input.nextLine();

            switch (option) {
                case "1":
                    // Displays sender and recipient for stored messages.
                    System.out.println(
                            Message.displayStoredMessageContacts()
                    );
                    break;

                case "2":
                    // Displays the longest message in the stored list.
                    System.out.println("Longest stored message:");
                    System.out.println(
                            Message.getLongestStoredMessage()
                    );
                    break;

                case "3":
                    // Finds a message using its message ID.
                    System.out.print("Enter the message ID: ");
                    String id = input.nextLine();

                    System.out.println(
                            Message.findMessageDetailsByID(id)
                    );
                    break;

                case "4":
                    // Finds all sent or stored messages for one recipient.
                    System.out.print("Enter the recipient number: ");
                    String recipient = input.nextLine();

                    System.out.println(
                            Message.findMessagesByRecipient(recipient)
                    );
                    break;

                case "5":
                    // Deletes a stored message using its message hash.
                    System.out.print("Enter the message hash: ");
                    String hash = input.nextLine();

                    System.out.println(
                            Message.deleteMessageByHash(hash)
                    );
                    break;

                case "6":
                    // Displays a report of every stored message.
                    System.out.println(
                            Message.createStoredMessageReport()
                    );
                    break;

                case "7":
                    // Closes this submenu and returns to QuickChat.
                    viewingStoredMessages = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please select 1 to 7."
                    );
            }
        }
    }

    // Allows the user to enter and send, disregard or store messages.
    public static void sendMessages(
            Scanner input, int numberOfMessages) {

        // Stops the user from entering more messages than selected.
        if (numberOfMessages == 0) {
            System.out.println(
                    "You have already entered your selected messages."
            );
            return;
        }

        // Repeats once for each message selected by the user.
        for (int i = 1; i <= numberOfMessages; i++) {
            System.out.println("\nMESSAGE " + i);

            // Generates an automatic 10-digit message ID.
            String messageID = Message.generateMessageID();

            System.out.println(
                    "Message ID generated: " + messageID
            );

            String recipient;

            // Keeps asking until the recipient number is valid.
            while (true) {
                System.out.print(
                        "Enter recipient cellphone number: "
                );

                recipient = input.nextLine();

                // Temporary message is used only to validate the number.
                Message temporaryMessage = new Message(
                        messageID,
                        i,
                        recipient,
                        ""
                );

                String recipientResult =
                        temporaryMessage.checkRecipientCell();

                System.out.println(recipientResult);

                if (recipientResult.equals(
                        "Cell phone number successfully captured.")) {
                    break;
                }
            }

            String messageText;

            // Keeps asking until the message is 250 characters or fewer.
            while (true) {
                System.out.print("Enter your message: ");

                messageText = input.nextLine();

                if (messageText.length() <= 250) {
                    break;
                } else {
                    System.out.println(
                            "Please enter a message of less than "
                            + "250 characters."
                    );
                }
            }

            // Creates the completed message object.
            Message newMessage = new Message(
                    messageID,
                    i,
                    recipient,
                    messageText
            );

            // Displays the automatically created message hash.
            System.out.println(
                    "Message Hash: "
                    + newMessage.createMessageHash()
            );

            // Lets the user select Send, Disregard or Store.
            String result = newMessage.sendMessage(input);

            System.out.println(result);

            // Displays all message details only when it was sent.
            if (newMessage.getMessageStatus().equals("Sent")) {
                System.out.println("Message ID: "
                        + newMessage.getMessageID());

                System.out.println("Message Hash: "
                        + newMessage.getMessageHash());

                System.out.println("Recipient: "
                        + newMessage.getRecipient());

                System.out.println("Message: "
                        + newMessage.getMessage());
            }
        }

        // Displays the total number of messages that were actually sent.
        System.out.println(
                "\nTotal messages sent: "
                + Message.returnTotalMessages()
        );
    }
}
