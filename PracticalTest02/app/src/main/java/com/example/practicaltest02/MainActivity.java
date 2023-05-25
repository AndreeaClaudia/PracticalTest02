package com.example.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ServerThread serverThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connect_button = findViewById(R.id.connect_button);
        Button set_button = findViewById(R.id.set_button);
        Button reset_button = findViewById(R.id.reset_button);
        Button poll_button = findViewById(R.id.poll_button);

        EditText serverPortEditText = findViewById(R.id.server_port_edit_text);

        connect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverPort = serverPortEditText.getText().toString();
                if (serverPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    serverThread = new ServerThread(Integer.parseInt(serverPort));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (serverThread.getServerSocket() == null) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Could not create server port", Toast.LENGTH_SHORT).show();
                    return;
                }
                serverThread.start();
            }
        });

        EditText clientAddressEditText = findViewById(R.id.client_address_edit_text);
        EditText clientPortEditText = findViewById(R.id.client_port_edit_text);

        EditText oraEditText = findViewById(R.id.ora_edit_text);
        EditText minutEditText = findViewById(R.id.minut_edit_text);

        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clientAddress = clientAddressEditText.getText().toString();
                String clientPort = clientPortEditText.getText().toString();

                if (clientAddress.isEmpty() || clientPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String ora = oraEditText.getText().toString();
                String minut = minutEditText.getText().toString();

                ClientThread clientThread = new ClientThread(clientAddress,Integer.parseInt(clientPort),Integer.parseInt(ora),Integer.parseInt(minut));
                clientThread.start();
            }
        });
    }
}