package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SchachServer {
    private static final int PORT = 7777;
    // * * * * * * * * all attributes made static * * * * * * * *
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static PrintWriter out = null;
    private static Schach spiel = null;

    public static void main(String[] args) {
        // * * * * * * * * missing try catch block added * * * * * * * *
        try {
            server = new ServerSocket(PORT);
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace(); // System.out.println("Port already in use");
        }
        // * * * * * * * * loop's position fixed * * * * * * * *
        while (true) {
            try {
                String msg = in.readLine();
                int type = Schach.getTyp(msg);
                if (type == 0) {
                    spiel = Schach.neuesSpiel();
                    out.println("0 OK");
                    out.flush();
                } else if (type == 1) {
                    String zugW = Schach.getInhalt(msg);
                    String zugS = Schach.ziehe(zugW);
                    out.println("1" + zugS);
                    out.flush();
                } else if (type == 2) { // make a function (void ifNotClosed) to simplify this block
                    out.println("2 OK");
                    out.flush();
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    out.println("Error, type unknown");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } // try catch
        } // while
    } // main
}
