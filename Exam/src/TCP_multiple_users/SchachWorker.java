package TCP_multiple_users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SchachWorker extends Thread {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Schach spiel = null;

    public SchachWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
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
                    break;
                } else {
                    out.println("Error, type unknown");
                    out.flush(); // Added by me
                }
            } catch (IOException e) {
                e.printStackTrace();
            } // try catch block
        } // while
    } // run
}
