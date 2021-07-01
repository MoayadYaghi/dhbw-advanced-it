import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static final int DEFAULT_PORT = 7777;
    // Filepath
    static final String FILEPATH = System.getProperty("user.home") + File.separator + "Desktop"+ File.separator + "Messages" + File.separator;

    // Declare variables
    int port;
    PrintWriter out;
    BufferedReader in;
    Socket connection = null;

    public Server(int port) {

        this.port = port;
        ServerSocket server = null;

        try {
            // Create a socket on the set port and assign it to the variable server
            server = new ServerSocket(this.port);
            while (true) {
                try {
                    // Listens for a connection
                    connection = server.accept();

                    // Create the inputs and outputs.
                    out = new PrintWriter(connection.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    // Read the input from the client
                    String input = in.readLine();
                    // Check if the client wants to create a file with content
                    if (input.startsWith("SAVE") || input.startsWith("SAVE ")) {
                        // Check whether an argument is put with the save command
                        if (input.length() > 5) {
                            // Generate key
                            String key =  generateKey();
                            // Generate file and use the key as its name
                            String fileName = generateFile(input.substring(5), key);
                            // If a file with this name doesn't already exist
                            if (!key.startsWith("FAILED ")) {
                                // Return key
                                out.println("KEY " + fileName);
                                // If a file with the same name already exists
                            } else if (key.startsWith("FAILED ")) {
                                // Print error
                                out.println(fileName);
                            }
                            // If the client didn't input arguments in the command
                        } else {
                            out.println("FAILED, no arguments!");
                        }
                        // Check if the client wants to read the content of specific file
                    } else if (input.startsWith("GET") || input.startsWith("GET ")) {
                        // Check whether an argument is put with the get command
                        if (input.length() > 4) {
                            // Get the key from the client
                            String value = getValue(input.substring(4));
                            // If there is no problems with reading the file
                            if (!value.startsWith("FAILED")) {
                                // Print content of the file
                                out.println("OK " + value);
                                // If a problem occurs when reading the file
                            } else {
                                // Print error
                                out.println(value);
                            }
                            // If the client didn't input arguments in the command
                        } else {
                            out.println("FAILED, no arguments!");
                        }
                        // If the client types an invalid command
                    } else {
                        // If the client inputs an invalid command
                        out.println("FAILED, " + input + " is not a valid command!");
                    }
                    out.flush();
                } catch (IOException e) {
                } finally {
                    if (connection != null) {
                        // Close all connections
                        connection.close();
                        in.close();
                        out.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    // Close the port
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Server(DEFAULT_PORT);
    }

    // Show the content of a file named by the key
    private String getValue(String key) {
        // Create a file named like the key and saved and the given path
        File file = new File(FILEPATH, key);
        BufferedReader bufferedReader = null;
        try {
            // Read the file
            bufferedReader = new BufferedReader(new FileReader(file));
            // Return content
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            return "FAILED, file not found ";
        } catch (IOException e) {
            return "FAILED, internal error ";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Generate a random key
    private String generateKey() {
       return "#%f" + (int) (Math.random() * 1000000000) + "k%#";
    }

    // Create a file
    private String generateFile(String value, String fileName) {
        // Create a file object in the "Messages" folder on the desktop
        File file = new File(FILEPATH, fileName);
        FileWriter fileWriter = null;
        try {
            // Create file and fill with the given content
            fileWriter = new FileWriter(file);
            fileWriter.write(value);
            fileWriter.flush();
            // Return key
            return fileName;
        } catch (IOException e) {
            return "FAILED, file already exists";
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
