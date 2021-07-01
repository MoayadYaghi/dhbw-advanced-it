import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static final int SERVER_PORT = 7777;

    public static void main(String[] args) {

        System.out.println("Program started!\n" + "Type SAVE <content> to create a file with this content\n" +
                "Type GET <key> to get the content of a file with named as this key\n" + "Type EXIT to exit the program");

        // Declare variables with initial values
        String hostname = "localhost";
        PrintWriter networkOut = null;
        BufferedReader networkIn = null;
        Socket socket = null;

        try {
            while (true) {
                // Create a connection
                socket = new Socket(hostname, SERVER_PORT);
                // Create the inputs and outputs
                networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                networkOut = new PrintWriter(socket.getOutputStream());
                // Reade the line of the terminal
                String theLine = userIn.readLine();
                if (theLine.equals("EXIT")) {
                    break;
                }
                // Send the terminal line to the server
                networkOut.println(theLine);
                networkOut.flush();
                // Show the return value from the server on the terminal
                System.out.println(networkIn.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    // Close all connections
                    socket.close();
                    networkIn.close();
                    networkOut.close();
                    System.out.println("Goodbye!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
