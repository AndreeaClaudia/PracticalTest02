package com.example.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private int ora;
    private int minut;
    private String address;
    private int port;
    Socket socket;
    public ClientThread(String adresa, int port, int ora, int minut){
        this.ora = ora;
        this.minut = minut;
        this.address = adresa;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            printWriter.println(ora);
            printWriter.flush();
            printWriter.println(minut);
            printWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
