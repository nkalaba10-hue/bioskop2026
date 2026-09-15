/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.client.communication;

import java.io.IOException;
import java.net.Socket;

import rs.ac.bg.fon.ai.communication.communication.Receiver;
import rs.ac.bg.fon.ai.communication.communication.Request;
import rs.ac.bg.fon.ai.communication.communication.Response;
import rs.ac.bg.fon.ai.communication.communication.Sender;

/**
 *
 * @author korisnik
 */
public class Communication {

    private static Communication instance;
    private Socket socket;

    private Communication() {
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    // Main method for sending requests
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public Response sendRequest(Request request) throws Exception {
        // PROVERA PRE SLANJA
        if (!isConnected()) {
            throw new Exception("Server nije aktivan. Povežite se na server.");
        }

        try {
            new Sender(socket).send(request);
            return (Response) new Receiver(socket).receive();
        } catch (Exception ex) {
            // Ako dodje do greške u komunikaciji, zatvori konekciju
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
            // ← NE WRAP-UJ IZUZETAK! BACI ORIGINALNI!
            throw ex;
        }
    }
}
