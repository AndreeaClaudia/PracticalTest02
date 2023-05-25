package com.example.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    private ServerSocket serverSocket = null;

    public ServerThread(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("TEST", "[SERVER THREAD] Waiting for a client invocation...");
                Socket socket = serverSocket.accept();
                Log.i("TEST", "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (IOException clientProtocolException) {
            Log.e("TEST", "[SERVER THREAD] An exception has occurred: " + clientProtocolException.getMessage());
        }
    }
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e("TEST", "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
            }
        }
    }
}
