# QuickChat – PROG5121 PoE (Part 3)

A console-based chat application written in Java. Users register and log in, then send, disregard or store messages. Stored messages are saved to a JSON file and can be displayed, searched, deleted and reported on.

## How to run
1. Open the project in NetBeans (Maven project, Java 25).
2. Run `JjaonePart1.java`.
3. Run the unit tests with `mvn test`.

## Features (Part 3)
- Arrays for sent, disregarded and stored messages, message hashes and message IDs
- Stored messages read from `stored_messages.json` into an array
- Stored Messages menu:
  - Display the sender and recipient of all stored messages
  - Display the longest stored message
  - Search by message ID
  - Search all messages for a particular recipient
  - Delete a message using the message hash
  - Display a report of all stored messages
- Report of all sent messages (hash, recipient and message) from the main menu
- JUnit tests (`MessageTest`, `LoginTest_1`), run automatically with GitHub Actions on every push

## Design notes
Messages are stored in ArrayLists (sent, disregarded, stored) of `Message` objects, with separate ArrayLists for message hashes and message IDs. Each `Message` object keeps its ID, hash, recipient and text together, so the data cannot become misaligned the way separate parallel arrays can.

The assignment's test table asks for a report of sent messages, while the menu asks for stored messages, so both reports are implemented.

## References

Fadatare, R. (2019) *JSON.simple Tutorial - Read and Write JSON in Java*. Available at: https://www.javaguides.net/2019/07/jsonsimple-tutorial-read-and-write-json-in-java.html (Accessed: 17 November 2026).

W3Schools (2026) *Java Tutorial*. Available at: https://www.w3schools.com/java/ (Accessed: 7 September 2026).
