package TCP_multiple_users;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SchachDispatcher {
    private static final int PORT = 7777;
    private static ServerSocket server = null;

    public static void main(String[] args) {
        // * * * * * * * * missing try catch block added * * * * * * * *
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) { // EXCEPTION (class) is enough
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = server.accept();
                Thread schachWorker = new SchachWorker(socket);
                schachWorker.start();
                schachWorker.sleep(1000); // FALSE????????????????
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
