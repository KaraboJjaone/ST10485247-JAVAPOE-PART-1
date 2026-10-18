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
    // Starts the QuickChat application
    public static void startQuickChat(Scanner input) {
         // Display welcome message
        
        System.out.println("Welcome to QuickChat.");

        // Ask how many messages the user wants to enter
        System.out.print("How many messages would you like to enter? ");
        int numberOfMessages = input.nextInt();

        // Clears the Enter key after nextInt()
        input.nextLine();

        // Controls whether the menu should continue showing
        boolean running = true;

        // Keep showing the menu while the program is running
        while (running) {

            // Display menu options
            System.out.println("\n---------- QUICKCHAT ----------");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            // Read the user's menu choice
            String option = input.nextLine();

            // Decide what to do based on the user's choice
            switch (option) {

                case "1":
                    // Start sending messages
                    sendMessages(input, numberOfMessages);
                    break;

                case "2":
                    // This feature is not ready yet
                    System.out.println("\nComing Soon.");
                    break;

                case "3":
                    // Stop the menu loop and exit QuickChat
                    running = false;
                    System.out.println("\nThank you for using QuickChat.");
                    break;

                default:
                    // Display an error if the user enters an invalid option
                    System.out.println(
                        "Invalid option. Please select 1, 2 or 3."
                    );
            }
        }
    }

    // Allows the user to enter and send messages
    public static void sendMessages(
            Scanner input, int numberOfMessages) {

        // Repeat for the number of messages chosen by the user
        for (int i = 1; i <= numberOfMessages; i++) {

            // Display the current message number
            System.out.println("MESSAGE " + i);

            // Generate a unique ID for the message
            String messageID = Message.generateMessageID();

            System.out.println(
                "Message ID generated: " + messageID
            );

            String recipient;

            // Keep asking until a valid cell phone number is entered
            while (true) {

                System.out.print(
                    "Enter recipient cellphone number: "
                );

                // Store the recipient number
                recipient = input.nextLine();

                // Create a temporary message to check the phone number
                Message temporaryMessage = new Message(
                    messageID,
                    i,
                    recipient,
                    ""
                );

                // Check if the recipient number is correct
                String recipientResult = temporaryMessage.checkRecipientCell();

                System.out.println(recipientResult);

                if (recipientResult.equals("Cell phone number successfully captured.")) {
                break;
                }
            }

            String messageText;

            // Keep asking until the message is 250 characters or fewer
            while (true) {

                System.out.print("Enter your message: ");

                // Store the message text
                messageText = input.nextLine();

                // Check the message length
                if (messageText.length() <= 250) {
                    break;
                } else {
                    System.out.println(
                        "Please enter a message of less than "
                        + "250 characters."
                    );
                }
            }

            // Create the final message object
            Message newMessage = new Message(
                messageID,
                i,
                recipient,
                messageText
            );

            // Display the automatically created message hash
            System.out.println(
                "Message Hash: "
                + newMessage.createMessageHash()
            );

            // Let the user choose to send, discard, or store the message
            String result = newMessage.sendMessage(input);

            // Display the result message
            System.out.println(result);

            // Only show full details if the message was sent
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

        // Display the final number of sent messages
        System.out.println(
            "\nTotal messages sent: "
            + Message.returnTotalMessages()
        );
    }
}
