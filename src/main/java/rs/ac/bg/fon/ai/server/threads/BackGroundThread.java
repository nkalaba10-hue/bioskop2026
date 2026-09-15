/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rs.ac.bg.fon.ai.server.logic.Controller;

/**
 *
 * @author nkala
 */
public class BackGroundThread extends Thread {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private volatile boolean running = true;

    @Override
    public void run() {
        System.out.println("BackgroundThread pokrenut - automatsko ažuriranje statusa projekcija");

        // Pokreni zadatak svakih 1 minut (možeš promeniti na 5 minuta ako želiš)
        scheduler.scheduleAtFixedRate(this::updateProjectionStatuses, 0, 1, TimeUnit.MINUTES);
    }

    private void updateProjectionStatuses() {
        if (!running) {
            return;
        }

        try {
            System.out.println("BackgroundThread: Ažuriranje statusa projekcija...");
            int updatedCount = Controller.getInstance().updateProjectionStatuses();
            if (updatedCount > 0) {
                System.out.println("BackgroundThread: Ažurirano " + updatedCount + " projekcija");
            }
        } catch (Exception ex) {
            System.out.println("BackgroundThread: Greška pri ažuriranju statusa: " + ex.getMessage());
        }
    }

    public void shutdown() {
        running = false;
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
        System.out.println("BackgroundThread zaustavljen");
    }
}
