package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


// this class handles all network communication for the client application.
// GUI classes will use this service to send and receive data.
public class NetworkService {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // try to connect to the server
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Host not found: " + host);
            return false;
        } catch (IOException e) {
            System.err.println("Could not connect to server: " + e.getMessage());
            return false;
        }
    }
    

    // send a chat message to the server
    // this is a *non-blocking* operation (it just sends, does not wait for response)
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    // return the input stream so that a background listener can read from it
    public BufferedReader getInputStream() {
        return in;
    }

    public PrintWriter getPrintWriter() {
        return out;
    }

    // close all connections
    public void disconnect() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
