package com.example.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread{
    private final ServerThread serverThread;
    private final Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e("TEST", "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            int ora = Integer.parseInt(bufferedReader.readLine());
            int minut = Integer.parseInt(bufferedReader.readLine());
            Log.i("TEST", "[COMMUNICATION THREAD] Ora: " + ora+" minut "+minut
            );
            Socket serverSocket = new Socket("utcnist.colorado.edu",13);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader serverReader = new BufferedReader(inputStreamReader);
           while (serverSocket.getKeepAlive()) {
               String timestamp = serverReader.readLine();
               Log.i("TEST", "[COMMUNICATION THREAD] Timestamp:" + timestamp);
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
