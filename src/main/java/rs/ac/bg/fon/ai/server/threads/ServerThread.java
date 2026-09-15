/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nkala
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private List<HandleClientThread> clients;
    private BackGroundThread backgroundThread;

    public ServerThread() throws IOException {
        serverSocket = new ServerSocket(9000);
        clients = new ArrayList<>();
        backgroundThread = new BackGroundThread();
    }

    @Override
    public void run() {
        // POKRENI BACKGROUND THREAD
        backgroundThread.start();
        System.out.println("BackgroundThread za automatsko ažuriranje statusa je pokrenut");

        while (!serverSocket.isClosed()) {
            System.out.println("Waiting for a client...");
            try {
                Socket socket = serverSocket.accept();
                HandleClientThread client = new HandleClientThread(socket);
                client.start();
                clients.add(client);

                // Čišćenje mrtvih konekcija
                cleanupDeadConnections();
            } catch (IOException ex) {
                if (!serverSocket.isClosed()) {
                    ex.printStackTrace();
                }
            }
        }
        stopAllHandleClientThreads();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    private void stopAllHandleClientThreads() {
        // ZAUSTAVI BACKGROUND THREAD
        if (backgroundThread != null) {
            backgroundThread.shutdown();
        }

        // Zatvori sve klijentske konekcije
        for (HandleClientThread client : clients) {
            try {
                client.getSocket().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        clients.clear();
    }

    private void cleanupDeadConnections() {
        clients.removeIf(client
                -> client.getSocket().isClosed() || !client.isAlive()
        );
    }
}
